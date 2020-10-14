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
        })
    },
    save : function () {
        var links = [];
        $('label[name=link]').each(function() {
            links.push($(this).text());
        });

        var formData = new FormData();
        formData.append("userCode", $('#userCode').val());
        formData.append("title", $('#title').val());
        formData.append("address", $('#address').val());
        formData.append("addressDetail", $('#address-detail').val());
        formData.append("links", links);
        formData.append("content", $('#content').val());
        formData.append("thumbnailPath", $('#thumbnail').val());
        formData.append("thumbnailFile", $('#thumbnail')[0].files[0]);

        $.ajax({
            type: 'POST',
            url: '/board/place/posts',
            dataType: 'json',
            processData: false,
            contentType: false,
            data: formData
        }).done(function (data) {
            alert('글이 등록되었습니다.');
            window.location.href='/board/place/posts/list';
        }).fail(function (error) {
            alert("save error : ");
            alert(JSON.stringify(error));
        });
    },
    update : function () {
        var links = [];
        $('label[name=link]').each(function() {
            links.push($(this).text());
        });

        var formData = new FormData();
        formData.append("title", $('#title').val());
        formData.append("address", $('#address').val());
        formData.append("addressDetail", $('#address-detail').val());
        formData.append("content", $('#content').val());
        formData.append("links", links);
        formData.append("oldThumbnailPath", $('#oldThumbnailPath').val());


        if($('#thumbnail')[0].files[0] !== undefined) {
            formData.append("thumbnailPath", $('#thumbnail').val());
            formData.append("thumbnailFile", $('#thumbnail')[0].files[0]);
        }

        $.ajax({
            type: 'PUT',
            url: '/board/place/posts/'+$('#postNo').val(),
            dataType: 'json',
            processData: false,
            contentType: false,
            data: formData
        }).done(function (postNo) {
            alert('글이 수정되었습니다.');
            window.location.href='/board/study/posts/read?postNo=' + postNo;
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete : function () {
        var postNo = $('#postNo').val();

        $.ajax({
            type: 'DELETE',
            url: '/board/place/posts/'+ postNo,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8'
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href='/board/place/posts/list';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();