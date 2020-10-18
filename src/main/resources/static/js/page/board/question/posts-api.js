var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            setConditionExplanation();
            _this.save();
        });
        $('#btn-update').on('click', function () {
            setConditionExplanation();
            _this.update()
        });
        $('#btn-delete').on('click', function () {
            _this.delete();
        })
    },
    save : function () {
        var data = {
            title : $('#title').val(),
            content : $('#content').val(),
            userCode : $('#userCode').val()
        };

        $.ajax({
            type: 'POST',
            url: '/board/question/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href='/board/question/posts/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var data = {
            title : $('#title').val(),
            content : $('#content').val()
        };

        var postNo = $('#postNo').val();

        $.ajax({
            type: 'PUT',
            url: '/board/question/posts/'+ postNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href='/board/question/posts?postNo=' + postNo;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        $.ajax({
            type: 'DELETE',
            url: '/board/question/posts/'+ $('#postNo').val(),
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href='/board/question/posts/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();