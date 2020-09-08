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

        console.log(data);

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
    var div = $(target).closest('div').nextAll('.div-rereply-save')[0];

    if($(div).css("display") === "none") {
        $(div).show()
    } else {
        $(div).hide();
    }
}

function rereply_save(target) {
    var div_reply = $(target).closest('.div-reply')[0];

    var data = {
        replyNo : $(div_reply).find('input[name=replyNo]')[0].value,
        content : $(div_reply).find('#rereply-content')[0].value,
        postNo : $('#postNo').val(),
        boardName : $('#boardName').val()
    };

    $.ajax({
        type: 'POST',
        url: '/board/study/posts/rereply/save',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function () {
        window.location.href='/board/study/posts/read?postNo='+data.postNo;
    }).fail(function (error) {
        alert(JSON.stringify(error));
    });



    // Long replyNo;
    // User user;
    // String content;
    // Long postNo;
    // String boardName;
}