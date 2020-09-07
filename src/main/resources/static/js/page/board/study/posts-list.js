var main = {
    init : function () {
        var _this = this;
        $('#btn-save').on('click', function () {
            setConditionExplanation();
            _this.save();
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
            url: '/board/study/posts/save',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href='/board/study/posts/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();