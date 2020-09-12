var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            setConditionExplanation();
            _this.save();
        });
        $('#btn-update').on('click', function () {
            setConditionExplanation();
            _this.update();
        });
        $('#btn-delete').on('click', function () {
            _this.delete();
        });
        $('#btn-search').on('click', function (e) {
            e.preventDefault();
            _this.search();
        });
    },
    save : function () {
        var data = {
            title: $('#title').val(),
            conditionLanguages: $('#conditionLanguages').val(),
            conditionPlace: $('#conditionPlace').val(),
            conditionStartDate: $('#startDate').val(),
            conditionEndDate: $('#endDate').val(),
            conditionCapacity: $('#checkbox-nolimit').is(':checked') ? 0 : $('#conditionCapacity').val(),
            conditionExplanation: $('#conditionExplanation').val()
        };

        $.ajax({
            type: 'POST',
            url: '/board/study/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href='/board/study/posts/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update : function() {
        var data = {
            userCode: $('#userCode').val(),
            title: $('#title').val(),
            conditionLanguages: $('#conditionLanguages').val(),
            conditionPlace: $('#conditionPlace').val(),
            conditionStartDate: $('#startDate').val(),
            conditionEndDate: $('#endDate').val(),
            conditionCapacity: $('#checkbox-nolimit').is(':checked') ? 0 : $('#conditionCapacity').val(),
            conditionExplanation: $('#conditionExplanation').val()
        };

        var postNo = $('#postNo').val();

        $.ajax({
            type: 'PUT',
            url: '/board/study/posts/'+ postNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href='/board/study/posts/read?postNo=' + postNo;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function() {
        var postNo = $('#postNo').val();

        $.ajax({
            type: 'DELETE',
            url: '/board/study/posts/'+ postNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href='/board/study/posts/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    search : function () {
        var category = $('#search-category option:selected').val();
        var keyword = $('#search-keyword').val();

        $('#form-search').attr('action', '/board/study/search/' + category + '/' + keyword + '/list');
        $('#form-search').submit();
    },
};

main.init();