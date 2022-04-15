function toggleValueFieldTextBox() {
    useTextForValue = document.getElementById('use-text-for-value').value
    valueTextArea = document.getElementById('alternate-value')
    if (valueTextArea.style.display == 'block') {
        valueTextArea.style.display = 'none'
    }
    else {
        valueTextArea.style.display = 'block'
    }
}

function toggleDependencyInputs() {
    dependsOnColumn = document.getElementById('depends-on').value
    if (dependsOnColumn != "") {
        document.getElementById('dependent-value').style.visibility = 'visible'
        document.getElementById('current-value').style.visibility = 'visible'
    }
    else {
        document.getElementById('dependent-value').style.visibility = 'hidden'
        document.getElementById('current-value').style.visibility = 'hidden'
    }
}