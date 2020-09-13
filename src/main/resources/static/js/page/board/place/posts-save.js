var oEditor = [];
var save_main = {
     init : function () {
         var _this = this;
        _this.loadEditorFrame();
        _this.loadAddressApi();

        $('#btn-add-link').on('click', function () {
            _this.addLink();
        });
        $('#btn-remove-link').on('click', function (e) {
            _this.removeLink(e.target);
        })
     },
    loadEditorFrame : function () {
        //스마트에디터 프레임생성
        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditor,
            elPlaceHolder: "conditionExplanation",
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
        var $addedNode = $('<div name="div-link"><button name="btn-remove-link">-</button><label>'+ addedLink +'</label></div>');

        $('#div-linksBox').prepend($addedNode);
        $('#add-link').val("");
    },
    removeLink : function (target) {
        var removedLink = $(target).closest('div[name=div-link]');
        $(removedLink).remove();
    }
};

var setConditionExplanation = function () {
    oEditor.getById["conditionExplanation"].exec("UPDATE_CONTENTS_FIELD", []);
};

save_main.init();

