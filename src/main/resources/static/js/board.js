var board={

    init: function(){

        $("#btn_save").on('click',()=>{

            var str = "";

            $(".uploadResult li").each(function(i,obj){     // obj -> $(".uploadResult li")
                var target = $(obj);

                console.log(target);

                str += "<input  name='boardImageDtoList["+i+"].imgName' value='"+target.data('name') +"'>";

                str += "<input  name='boardImageDtoList["+i+"].path' value='"+target.data('path')+"'>";

                str += "<input  name='boardImageDtoList["+i+"].uuid' value='"+target.data('uuid')+"'>";

            });

            //태그들이 추가된 것을 확인한 후에 comment를 제거
            $(".box").html(str);

            //this.save();
            $("form").submit();
        }),

        $("#btn_update").on('click',()=>{
            this.update();

        }),
        $("#btn_delete").on('click',()=>{
            this.delete();

        })


    },

    save: function(){

        var data ={
            title : $('#title').val(),
            content : $('#content').val()
        };


        console.log(data);

        $.ajax({
            type: 'POST',
            url: '/api/v1/board',
            dataType: 'json',
            contentType: 'application/json; charset = utf-8',
            data: JSON.stringify(data)

        }).done(function(vv){

            alert(JSON.stringify(vv));

            if(vv.status == 200){
                alert("글이 등록되었습니01다");
                location.href = '/board/list';
            }else{

                alert(vv.data)
            }

        }).fail(function(error){

            alert(JSON.stringify(error));
            alert("글 등록 에러");
        });
    },

    update: function(){

        var id = $('#boardId').val();

        var data = {

            title : $('#title').val(),
            content : $('#content').val()

        };

        console.log(data);
        console.log(id);

        $.ajax({

            type:'PUT',
            url:'/api/v1/board/'+id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)

        }).done(function(AA){

            alert("글이 수정되었습니다.");
            alert(AA);
            location.href="/board/list";
        }).fail(function(error){

            alert(JSON.stringify(error))
            alert("글 수정 에러")

        });

    },
    delete: function(){


         var boardId = $('#boardId').val();

         console.log(boardId);


         $.ajax({

            type: 'DELETE',
            url:'/api/v1/board/'+boardId,
            dataType: 'json',


         }).done(function(aa){

            alert('글이 삭제되었습니다');
            alert(aa);
            location.href ='/board/list';

         }).fail(function(error){

            alert(JSON.stringify(error));
            alert('글 삭제 에러');

         })

    }


}

board.init();





// $('#testbt').click(function() {
//      $('#test').toggleClass('red');
//   })