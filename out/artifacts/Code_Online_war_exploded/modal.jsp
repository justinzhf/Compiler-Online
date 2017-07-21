<%--
  Created by IntelliJ IDEA.
  User: zhf
  Date: 2016/8/3
  Time: 18:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

    <link rel=stylesheet href="codemirror-5.12/doc/docs.css">
    <link rel="stylesheet" href="bootstrap-3.3.6/css/bootstrap.min.css">
    <link rel="stylesheet" href="codemirror-5.12/lib/codemirror.css">
    <link rel="stylesheet" href="codemirror-5.12/theme/eclipse.css">
    <link rel="stylesheet" href="codemirror-5.12/theme/seti.css">
    <link rel="stylesheet" href="codemirror-5.12/theme/dracula.css">
    <link rel="stylesheet" href="codemirror-5.12/addon/display/fullscreen.css">
    <script src="./js/jquery.min.js"></script>
    <script src="codemirror-5.12/lib/codemirror.js"></script>
    <script src="codemirror-5.12/clike.js"></script>
    <script src="codemirror-5.12/addon/selection/active-line.js"></script>
    <script src="codemirror-5.12/addon/edit/matchbrackets.js"></script>
    <script src="codemirror-5.12/addon/display/fullscreen.js"></script>
    <script src="bootstrap-3.3.6/js/bootstrap.min.js"></script>
    <title>Title</title>
    <h2>创建模态框（Modal）</h2>
    <!-- 按钮触发模态框 -->
    <button class="btn btn-primary btn-lg" data-toggle="modal"
            data-target="#myModal">
        开始演示模态框
    </button>

    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog"
         aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
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
                        <input rows="1" id="input" class="form-control"></input>
                    </div>
                    <button type="button" class="btn btn-primary">
                        输入
                    </button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</head>
<body>

</body>
</html>
