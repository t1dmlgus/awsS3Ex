var like ={

    init: function(){

            $('#btn-heart').click(function(){           // 좋아요 클릭

                    if($('#user').val() != null){

                        if($(this).children('.xi-heart-o').hasClass('xi-heart-o')){

                            $(this).children('.xi-heart-o').removeClass('xi-heart-o');
                            $(this).children().addClass('xi-heart');

                            //console.log(this);
                            like.like();

                        }else{
                            $(this).children('.xi-heart').removeClass('xi-heart');
                            $(this).children().addClass('xi-heart-o');

                            like.unlike();

                        }

                    }else{

                        alert("로그인이 필요한 서비스입니다.(좋아요)");
                        location.href="/auth/user/loginForm";

                    }

                    console.log(this.className);
                });


    },

        like: function(){

            alert("좋아요👍");

            var data = {

                userId : $('#user').val(),
                boardId : $('#boardId').val()

            };

            console.log(data.userId);
            console.log(data.boardId);

            $.ajax({

                type: "POST",
                url: "/api/v1/likes",
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)

            }).done(function(aa){

                alert('좋아요 등록되었습니다');
                alert(JSON.stringify(aa));
                console.log(aa);
        //            var t = console.log(JSON.stringify(aa));
                console.log(aa.data);
        //            console.log(aa.data.count);
        //            alert(aa.data.count);
        //
        //            var count01 = aa.data.count;

                $('#likeCount').html("좋아유 " +aa.data.count+ "개");
                //$('#likeCount').html(aa.data.count);    // 좋아요 카운트
                $('#btn-heart').val(aa.data.likeId);    // 좋아요 번호


            }).fail(function(error){

                alert(JSON.stringify(error));
                alert('좋아요 등록 에러');
            })


        },
        unlike: function(){


            alert("좋아요 취소👍");

            var data = {
                        userId : $('#user').val(),
                        boardId : $('#boardId').val(),
                        likeId : $('#btn-heart').val()

                    };


            console.log(data);


            $.ajax({

                type: "DELETE",
                url: "/api/v1/likes",
                dataType: 'json',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)

            }).done(function(aa){

                alert('좋아요 취소되었습니다');
                alert(JSON.stringify(aa));
                console.log(aa);
        //            var t = console.log(JSON.stringify(aa));
        //            console.log(aa.data);
        //            console.log(aa.data.count);
        //            alert(aa.data.count);
        //
        //            var count01 = aa.data.count;

                $('#likeCount').html("좋아유 " +aa.data+ "개");    // 좋아요 카운트

            }).fail(function(error){

                alert(error);
                alert('좋아요 취소 에러');

            })



        }


}

like.init();




