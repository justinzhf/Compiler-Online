<%@ page import="com.codeonline.publicString.PublicString" %><%--
  Created by IntelliJ IDEA.
  User: zhf
  Date: 2016/8/4
  Time: 15:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<meta name="viewport" content="width=device-width,
                                     initial-scale=1.0,
                                     maximum-scale=1.0,
                                     user-scalable=no">
<link rel=stylesheet href="codemirror-5.12/doc/docs.css">
<link rel="stylesheet" href="bootstrap-3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" href="codemirror-5.12/lib/codemirror.css">
<link rel="stylesheet" href="codemirror-5.12/theme/eclipse.css">
<link rel="stylesheet" href="codemirror-5.12/theme/seti.css">
<link rel="stylesheet" href="codemirror-5.12/theme/dracula.css">
<link rel="stylesheet" href="codemirror-5.12/addon/display/fullscreen.css">

<link rel="stylesheet" href="codemirror-5.12/addon/dialog/dialog.css">
<link rel="stylesheet" href="codemirror-5.12/addon/hint/show-hint.css">


<script src="./js/jquery.min.js"></script>
<script src="./js/jquery.hotkeys.js"></script>
<script src="codemirror-5.12/lib/codemirror.js"></script>
<script src="codemirror-5.12/clike.js"></script>
<script src="codemirror-5.12/mode/python/python.js"></script>
<script src="codemirror-5.12/addon/selection/active-line.js"></script>
<script src="codemirror-5.12/addon/edit/matchbrackets.js"></script>
<script src="codemirror-5.12/addon/display/fullscreen.js"></script>
<script src="codemirror-5.12/keymap/emacs.js"></script>
<script src="codemirror-5.12/keymap/vim.js"></script>
<script src="bootstrap-3.3.6/js/bootstrap.min.js"></script>
<script src="codemirror-5.12/addon/dialog/dialog.js"></script>
<script src="codemirror-5.12/addon/search/searchcursor.js"></script>
<script src="codemirror-5.12/addon/hint/show-hint.js"></script>
<script src="codemirror-5.12/addon/hint/javascript-hint.js"></script>
<script src="codemirror-5.12/addon/hint/anyword-hint.js"></script>

<link rel="stylesheet" href="codemirror-5.12/addon/fold/foldgutter.css" />
<script src="codemirror-5.12/addon/fold/foldcode.js"></script>
<script src="codemirror-5.12/addon/fold/foldgutter.js"></script>
<script src="codemirror-5.12/addon/fold/brace-fold.js"></script>
<script src="codemirror-5.12/addon/fold/comment-fold.js"></script>

<link href="./css/cover.css" rel="stylesheet">

<head>
    <title>Code Online 0.3</title>
</head>
<body >
<div class="navbar navbar-inverse" role="navigation">
    <div class="navbar-header">
        <a class="navbar-brand">Code Online 0.3</a>
    </div>
    <ul class="nav navbar-nav">
        <li class="active"><a href="#">code</a></li>
        <li><a href="help.html">帮助</a></li>
        <li><a href="about.html">关于我们</a></li>
    </ul>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-2">
            <select class="form-control" id="selectLanguage"  onchange="selectLanguage()">
                <option>c/c++</option>
                <option>java</option>
                <option>python</option>
            </select>
        </div>
    </div>
    <textarea id="code"></textarea>
    <span class="glyphicon glyphicon-cog"  role="button"
          data-container="body"  data-placement="top"
           data-html="true" id="popover"></span>
    &nbsp;&nbsp;&nbsp;
    <span class="glyphicon glyphicon-resize-full" role="button" onclick="enterFullScreen()" data-toggle="tooltip" title="Alt+Shift+Enter"></span>

</div>
<br/>
<div class="container">
    <div style="font-size: 25px;" class="text-center" data-toggle="tooltip" title="Ctrl+F7">
        <span class="glyphicon glyphicon-play" role="button"   data-toggle="modal"
              data-target="#io" id="code_submit"></span>
    </div>

</div>

<!-- 模态框（Modal） -->
<div class="modal fade" id="io" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true" data-backdrop="false"  >
    <div class="modal-dialog" >
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close"
                        data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    输出
                </h4>
            </div>
            <textarea readonly="readonly" class="form-control" cols="25" rows="23" id="output"></textarea>
            <div class="modal-footer">
                <div class="col-xs-10">
                    <input rows="1" id="input" class="form-control" ></input>
                </div>
                <button type="button" class="btn btn-primary" id="input_submit" data-toggle="tooltip" title="回车">
                    输入
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>


</body>
<script>
    var sessionid="<%=session.getId()%>";

    $(function () { $("#popover").popover({content:'<text class="text-primary">主题</text>'+
                                            '<select class="form-control" onchange="selectTheme()" id="selectTheme">'+
                                            '<option>经典</option>'+
                                            '<option>白天</option>'+
                                            '<option>夜间</option>'+
                                            '</select><br/>'+
                                            '<text class="text-primary">键盘</text>'+
                                            '<select class="form-control" onchange="selectKeyBoard()" id="selectKeyBoard">'+
                                            '<option>默认</option>'+
                                            '<option>Vim</option>'+
                                            '<option>Emacs</option>'+
                                            '</select>'});
    });
    $(function () { $("#popover").on('shown.bs.popover', function () {
        var themeOptions=document.getElementById("selectTheme").getElementsByTagName("option");
        var curTheme=editor.getOption("theme");
        if(curTheme=="seti"){
            themeOptions[0].selected=true;
        }else if(curTheme=="eclipse"){
            themeOptions[1].selected=true;
        }else if(curTheme=="dracula"){
            themeOptions[2].selected=true;
        }

        var keyBoardOptions=document.getElementById("selectKeyBoard").getElementsByTagName("option");
        var curKeyBoard=editor.getOption("keyMap");
        if(curKeyBoard=="default"){
            keyBoardOptions[0].selected=true;
        }else if(curKeyBoard=="vim"){
            keyBoardOptions[1].selected=true;
        }else if(curKeyBoard=="emace"){
            keyBoardOptions[0].selected=true;
        }
    })});


    var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
        lineNumbers: true,
        styleActiveLine: true,
        matchBrackets: true,
        mode: {name: "text/x-c", globalVars: true},
        width: "100%",
        height: "100%",
        keyMap:"default",
        lineWrapping: true,
        foldGutter: true,
        gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
        indentUnit:4,
        showCursorWhenSelecting: true,
        extraKeys: {
            "Shift-Alt-Enter": function (cm) {
                cm.setOption("fullScreen", !cm.getOption("fullScreen"));
            },
            "Ctrl-Space": "autocomplete",
            "Ctrl-F7":function(){$('#code_submit').click();}
        }
    });
    editor.setOption("theme", "seti");
    editor.setOption("keyMap" , "default");
    editor.setSize("100%", "500");
    var hint='/*Due to the linux system reason,you should add "fflush(stdout)" after a \n ' +
            'standard output statement(especially printf()).Like this,"printf("Hello world!");fflush(stdout);"\n' +
            'Otherwise,the I/O sequences may not correct.*/'
    editor.setValue(hint);


    function selectLanguage(){
        var lang=$("#selectLanguage").val();
        if(lang=="c/c++"){
            editor.setOption("mode","text/x-c");
            editor.setValue(hint);

        }else if(lang=="java"){
            editor.setOption("mode","text/x-java");
            editor.setValue("/*name of java public class must be CodeOnline*/");
        }else if(lang=="python"){
            editor.setOption("mode","python");
            editor.setValue("");
        }

    }


    function selectTheme() {
        var input = document.getElementById("selectTheme");
        var theme = input.options[input.selectedIndex].textContent;

        if (theme == "经典") {
            editor.setOption("theme", "seti");
        } else if (theme == "白天") {
            editor.setOption("theme", "eclipse");
        } else if (theme == "夜间") {
            editor.setOption("theme", "dracula");
        }
    }

    function selectKeyBoard() {
        var input = document.getElementById("selectKeyBoard");
        var theme = input.options[input.selectedIndex].textContent;
        if (theme == "默认") {
            editor.setOption("keyMap" , "default");
        } else if (theme == "Vim") {
            editor.setOption("keyMap", "vim");
        } else if (theme == "Emacs") {
            editor.setOption("keyMap", "emacs");
        }
    }


    function enterFullScreen(){
        editor.setOption("fullScreen",true);
    }



    $(function () { $('#io').on('show.bs.modal', function () {
        $('#output').val('编译中...\n');
        $('#input').val('');
    })
    });

    $(function () { $('#io').on('hide.bs.modal', function () {
        $.post("./index.action",
                {

                },
                function (data, status) {

                });
    })
    });



    $('#input').bind('keypress',function(event){
        if(event.keyCode == "13") {
            $('#input_submit').click();
        }
    });

    var webSocket =
            new WebSocket("<%=PublicString.WEBSOCKET_ADDRESS%>");
    webSocket.onerror = function (event) {
        onError(event)
    };
    webSocket.onopen = function (event) {
        onOpen(event)
    };
    webSocket.onmessage = function (event) {
        onMessage(event)
    };
    function onMessage(event) {
        document.getElementById("output").value+=event.data;
    }
    function onOpen(event) {
        webSocket.send(sessionid);
    }
    function onError(event) {
        alert("an error occurred");
    }
    function start() {
        webSocket.send(sessionid);
        return false;
    }
    $(document).ready(function () {
        $(document).bind('keydown', 'ctrl+f7', function(){$('#code_submit').click();})
        $("#code_submit").click(function () {
            start();
            $.post("./run.action",
                    {
                        code: editor.getValue(),
                        language:$("#selectLanguage").val()
                    },
                    function (data, status) {

                    });
        });
        $("#input_submit").click(function () {
            $('#output').val($('#output').val()+$("#input").val()+'\n');
            $.post("./input.action",
                    {
                        input: $("#input").val()
                    },
                    function (data, status) {

                    });
            $("#input").val("");
        });
    });





</script>
</html>
