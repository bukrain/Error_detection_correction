
function callHighlightDiff(data){
    if(data.status === "success"){
        highlightDiff($('#form\\:message_with_errors'), $('#form\\:message_with_hamming'), $('#form\\:message_after_correction'));
    }
}

function highlightDiff(newElem, oldElem, middleElement){
    var oldText = oldElem.text(),
        newText = newElem.text(),
        text = '',
        textOld = '',
        middleText = '';
    newElem.text().split('').forEach(function(val, i){
        if (val !== oldText.charAt(i)) {
            text += "<span class='highlight'>" + val + "</span>";
            textOld += "<span class='highlight2'>" + oldText.charAt(i) + "</span>";
        }
        else {
            text += val;
            textOld += val;
        }
    });

    middleElement.text().split('').forEach(function (val, i) {
        if(val !== newText.charAt(i)){
            middleText += "<span class='highlight3'>" + val + "</span>";
        }else{
            middleText += val;
        }
    });
    if(text.length !== 0 && textOld.length !== 0 && middleText.length !== 0) {
        newElem.html(text);
        oldElem.html(textOld);
        middleElement.html(middleText);
    }
}