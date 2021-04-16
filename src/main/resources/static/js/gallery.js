var gallery ={


    init: function(){

        $("#btn_save").on('click',()=>{

            var file = $('title').val;
            console.log("~!~!");

            // 01. Form 에 작성된 전체 데이터보내기
            var formData = new FormData($("#upload01")[0]);

            console.log("_______________");

            for(var value of formData.values()){
                console.log(value);

            }

            this.save(formData);
        });

//        $(".btn_update").on('click',()=>{
//
//            var form = $(".id").val();
//
//            console.log("수정하기");
//            console.log(form);
//
//            var formData = new FormData($(form)[0]);
//
//            for(var value of formData.values()){
//                console.log(value);
//
//            }
//
//
//
//        })

    },

    save: function(formData){

        console.log("upload 함수");

        for(var value of formData.values()){
            console.log(value);
        }

        $.ajax({

                url: '/uploadGallery',
                processData: false,
                contentType: false,
                data: formData,
                type: 'POST',
                dataType:'json',
                success: function(result){
                    console.log("result :" + result);
                    location.href = '/gallery';
                },
                error: function(jqXHR, textStatus, errorThrown){
                    console.log(textStatus);
                }

        });


    },
    update : function(vv){

        console.log(vv);

        var form = "detail"+vv;

        console.log(form);

        var formData = new FormData($(".detail"+vv)[0]);

        for(var value of formData.values()){
            console.log(value);
        }

        $.ajax({

            url: '/uploadGallery',
            processData: false,
            contentType: false,
            data: formData,
            type: 'POST',
            dataType:'json',
            success: function(result){
                console.log("result :" + result);
                location.href = '/gallery';
            },
            error: function(jqXHR, textStatus, errorThrown){
                console.log(textStatus);
            }

        });



    }



}

gallery.init();