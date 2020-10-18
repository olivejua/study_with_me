var main = {
    init           : function () {
        var _this = this;

        $('button[name=open-comment-modify]').on('click', function (e) {
            _this.openCommentModify(e.target);
        });
        $('button[name=open-reply-save]').on('click', function (e) {
            _this.openReplySave(e.target);
        });


        $('button[name=btn-comment-save]').on('click', function () {
            _this.commentSave();
        });
        $('button[name=btn-comment-modify]').on('click', function (e) {
            _this.commentModify(e.target);
        });
        $('button[name=btn-comment-delete]').on('click', function (e) {
            _this.commentDelete(e.target);
        });


        $('button[name=btn-reply-save]').on('click', function (e) {
            _this.replySave(e.target);
        });
        $('button[name=btn-reply-modify]').on('click', function (e) {
            _this.replyModify(e.target);
        });
        $('button[name=btn-reply-delete]').on('click', function (e) {
            _this.replyDelete(e.target);
        });


        $('.div-reply-save').hide();
    },
    commentSave           : function () {
        var data = {
            postNo: $('#postNo').val(),
            boardName: $('#boardName').val(),
            userCode: $('#userCode').val(),
            content: $('#comment-content').val()
        };

        console.log(data);

        $.ajax({
            type: 'POST',
            url: '/board/posts/comment',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            window.location.href='/board/study/posts?postNo='+data.postNo;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    commentModify : function (target) {
        var div = $(target).closest('div[name=div-one-comment]')[0];

        var data = {
            commentNo: $(div).children('input[name=commentNo]')[0].value,
            content: $(target).prev().val()
        };

        $.ajax({
            type: 'PUT',
            url: '/board/posts/comment/'+data.commentNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            window.location.href='/board/study/posts?postNo='+$('#postNo').val();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    commentDelete : function (target) {
        var div = $(target).closest('div[name=div-one-comment]')[0];
        var commentNo = $(div).children('input[name=commentNo]')[0].value;

        $.ajax({
            type: 'DELETE',
            url: '/board/posts/comment/'+commentNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function () {
            window.location.href='/board/study/posts?postNo='+$('#postNo').val();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    replySave : function (target) {
        var div = $(target).closest('div[name=wrapped-each-comment]');

        var data = {
            commentNo : $(div).find('input[name=commentNo]')[0].value,
            content : $(div).find('textarea[name=reply-content]')[0].value,
            postNo : $('#postNo').val(),
            boardName : $('#boardName').val()
        };

        $.ajax({
            type: 'POST',
            url: '/board/posts/reply',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            window.location.href='/board/study/posts?postNo='+data.postNo;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    replyModify : function (target) {
        var div = $(target).closest('div[name=wrapped-each-reply]');

        var data = {
            replyNo : $(div).find('input[name=replyNo]')[0].value,
            content : $(div).find('textarea[name=reply-modify-content]')[0].value
        };

        $.ajax({
            type: 'PUT',
            url: '/board/posts/reply/' + data.replyNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            window.location.href='/board/study/posts?postNo='+$('#postNo').val();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    replyDelete : function (target) {
        var div = $(target).closest('div[name=wrapped-each-reply]');
        var replyNo = $(div).find('input[name=replyNo]')[0].value;

        $.ajax({
            type: 'DELETE',
            url: '/board/posts/reply/'+ replyNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function () {
            window.location.href='/board/study/posts?postNo='+$('#postNo').val();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    openCommentModify: function (target) {
        var wrappedDiv = $(target).closest('div');

        var p = $(wrappedDiv).children('p[name=p-comment-modify]')[0];
        var div = $(wrappedDiv).children('div[name=div-comment-modify]')[0];

        if($(div).css('display') === 'none') {
            $(div).css('display', 'flex');
            $(p).css('display', 'none');
        } else {
            $(p).css('display', 'flex');
            $(div).css('display', 'none');
        }
    },
    openReplySave: function (target) {
        var div = $(target).closest('div').nextAll('.div-reply-save')[0];

        if($(div).css("display") === "none") {
            $(div).show()
        } else {
            $(div).hide();
        }
    }
};

main.init();