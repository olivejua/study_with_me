var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            setConditionExplanation();
            _this.save();
        });
    },
    save : function () {
        var links = [];
        $('label[name=link]').each(function() {
            links.push($(this).text());
        });

        var data = {
            userCode : $('#userCode').val(),
            title : $('#title').val(),
            address : $('#address').val(),
            addressDetail : $('#address-detail').val(),
            links : links,
            existThumbnail : $('#thumbnail').val() !== "",
            content : $('#content').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/board/place/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function (postNo) {
            console.log('saved!!! postNo: ' + postNo);

            $.ajax({
                type: 'POST',
                enctype: 'multipart/form-data',
                url: '/board/place/posts/upload/' + postNo,
                data: new FormData(document.getElementById('form-post')),
                processData: false,
                contentType: false,
            }).done(function () {
                alert('글이 등록되었습니다.');
                window.location.href='/board/place/posts/list';
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();