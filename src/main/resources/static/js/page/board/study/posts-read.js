var main = {
    init: function () {
        var _this = this;
        $('#btn-reply-save').on('click', function () {
            _this.save();
        });
    },
    save: function () {
        var data = {
            postNo: $('#postNo').val(),
            boardName: $('#boardName').val(),
            content: $('#reply_content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/board/study/posts/reply/save',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            window.location.href='/board/study/posts/read?postNo='+data.postNo;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();