<%--
  Created by IntelliJ IDEA.
  User: zhf
  Date: 2016/8/3
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<title>CodeMirror: Autocomplete Demo</title>
<meta charset="utf-8"/>
<link rel=stylesheet href="codemirror-5.12/doc/docs.css">

<link rel="stylesheet" href="codemirror-5.12/lib/codemirror.css">
<link rel="stylesheet" href="codemirror-5.12/addon/hint/show-hint.css">
<script src="codemirror-5.12/lib/codemirror.js"></script>
<script src="codemirror-5.12/addon/hint/show-hint.js"></script>
<script src="codemirror-5.12/addon/hint/anyword-hint.js"></script>
<script src="codemirror-5.12/mode/clike/clike.js"></script>


<link rel="stylesheet" href="codemirror-5.12/addon/fold/foldgutter.css" />
<script src="codemirror-5.12/addon/fold/foldcode.js"></script>
<script src="codemirror-5.12/addon/fold/foldgutter.js"></script>
<script src="codemirror-5.12/addon/fold/brace-fold.js"></script>
<script src="codemirror-5.12/addon/fold/comment-fold.js"></script>

<article>
    <h2>Autocomplete Demo</h2>
    <textarea id="code" name="code">
function getCompletions(token, context) {
  var found = [], start = token.string;
  function maybeAdd(str) {
    if (str.indexOf(start) == 0) found.push(str);
  }
  function gatherCompletions(obj) {
    if (typeof obj == "string") forEach(stringProps, maybeAdd);
    else if (obj instanceof Array) forEach(arrayProps, maybeAdd);
    else if (obj instanceof Function) forEach(funcProps, maybeAdd);
    for (var name in obj) maybeAdd(name);
  }

  if (context) {
    // If this is a property, see if it belongs to some object we can
    // find in the current environment.
    var obj = context.pop(), base;
    if (obj.className == "js-variable")
      base = window[obj.string];
    else if (obj.className == "js-string")
      base = "";
    else if (obj.className == "js-atom")
      base = 1;
    while (base != null && context.length)
      base = base[context.pop().string];
    if (base != null) gatherCompletions(base);
  }
  else {
    // If not, just look in the window object and any local scope
    // (reading into JS mode internals to get at the local variables)
    for (var v = token.state.localVars; v; v = v.next) maybeAdd(v.name);
    gatherCompletions(window);
    forEach(keywords, maybeAdd);
  }
  return found;
}</textarea>


    <script>
        var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
            lineNumbers: true,
            lineWrapping: true,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
            extraKeys: {"Ctrl-Space": "autocomplete",
                "Ctrl-Q": function(cm){ cm.foldCode(cm.getCursor()); }
                    },
            mode: {name: "text/x-java", globalVars: true}
        });

    </script>

</article>
</html>

