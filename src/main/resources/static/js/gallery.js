var gallery ={


    init: function(){

        $("#btn_upload").on('click',()=>{

            var file = $('title').val;
            console.log("~!~!");

            // 01. Form 에 작성된 전체 데이터보내기
            var formData = new FormData($("#upload01")[0]);

            console.log("_______________");

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


        });


    },

    upload: function(){

        console.log("upload 함수");
        console.log(formData);


        var data = new Data;

        console.log("2222222222222222222222");
        for(var value of formData.values()){
            console.log(value);
        }
         for(var value of formData.keys()){
                    console.log(value);
         }




    }


}

gallery.init();