var reply ={


    init: function(){


         $("#reply_save").on('click',()=>{

                console.log(this);
                this.replySave();
         })



    },
     saveReply: function(aa){

                   var listGroup = $(".list-group");
                   var replyContent = aa.data.replyContent;
                   var replyMember = aa.data.memberName;
                   var replyId = aa.data.replyId;

                    $('#reply_content').val("");


                   var str ="";

                    str += "<li class='replys list-group-item d-flex justify-content-between'>"
                    str += "<div>" + replyContent + "</div>"
                    str += "<div class='d-flex'>"
                    str += "<div class='font-italic reply_user'>"+ replyMember + "</div>"
                    str += "<button onclick='reply.replyDelete(" + replyId + ")' class='badge'>삭제</button>"
                    str += "</li>"

                    console.log(str);

                   $(".list-group").append(str);

     },

    replySave: function(){

            var data = {

                boardId : $('#boardId').val(),
                content : $('#reply_content').val()
            };

           console.log(data);

           $.ajax({

              type: 'POST',
              url:'/api/v1/reply',
              dataType: 'json',
              contentType: 'application/json; charset=utf-8',
              data: JSON.stringify(data)


           }).done(function(aa){

              alert('댓글이 등록되었습니다');
              console.log(aa);
              console.log(aa.data);

              reply.saveReply(aa);              // 비동기함수


           }).fail(function(error){

              alert(JSON.stringify(error));
              alert('댓글 등록 에러');

           })

    },
    replyDelete: function(vv){

            var id = $('#boardId').val();

            var data = {
                replyId : vv

            }

            console.log(data.replyId);
            console.log("gdgdg");
            console.log(vv);


            $.ajax({

               type: 'DELETE',
               url:'/api/v1/reply/'+vv,
               dataType: 'json',
               contentType: 'application/json; charset=utf-8',
               data: JSON.stringify(data)                              // 서버에 보낼 데이터

            }).done(function(aa){

               alert('댓글이 삭제되었습니다');

               location.href ='/board/detail?id='+id;

            }).fail(function(error){

               alert(JSON.stringify(error));
               alert('댓글 삭제 에러');

            })

    }



}

reply.init();