var oEditor = [];
var update_main = {
     init : function () {
         var _this = this;
        _this.loadEditorFrame();
        _this.loadAddressApi();

        $('#btn-add-link').on('click', function () {
            _this.addLink();
        });

        $('#thumbnail').on('change', function (e) {
            _this.loadNewThumbnail(e);
        })
     },
    loadEditorFrame : function () {
        //스마트에디터 프레임생성
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditor,
            elPlaceHolder: "content",
            sSkinURI: "/api/se2/SmartEditor2Skin.html",
            htParams : {
                // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
                bUseToolbar : true,
                // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
                bUseVerticalResizer : true,
                // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
                bUseModeChanger : true,
            }
        });
    },
    loadAddressApi : function () {
        //주소 api
        $("#postcodify_search_button").postcodifyPopUp();
    },
    addLink : function () {
        var addedLink = $('#add-link').val();
        var addedNode = $('<div name="div-link"><button type="button" onclick="removeLink(this)">-</button><label name="link">'+ addedLink +'</label></div>');

        $('#div-linksBox').prepend(addedNode);
        $('#add-link').val("");
    },
    loadNewThumbnail : function (event) {
         var reader = new FileReader();

         $('img[name=oldThumbnailPath]').css('display', 'none');

         reader.onload = function (event) {
             var img = document.createElement("img");
             img.setAttribute("src", event.target.result);
             document.querySelector('div#preview-thumbnail').appendChild(img);
         };

         reader.readAsDataURL(event.target.files[0]);
    }
};

var setConditionExplanation = function () {
    oEditor.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
};

var removeLink = function (target) {
    $(target).closest('div[name=div-link]').remove();
};

update_main.init();

