var main = {
    init: function () {
        var _this = this;
        $('#btn-reply-save').on('click', function () {
            _this.save();
        });

        $('.div-rereply-save').hide();
    },
    save: function () {
        var data = {
            postNo: $('#postNo').val(),
            boardName: $('#boardName').val(),
            content: $('#reply-content').val()
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


function rereply_open(target) {
    var div = $(target).closest('div').next();

    if(div.css("display") === "none") {
        div.show()
    } else {
        div.hide();
    }
}

function rereply_save(target) {
    var replyId = $(target).id;


}