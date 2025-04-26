function buscar(){
    var tableReg = document.getElementById('datos');
    var searchText = document.getElementById('buscar').value.toLowerCase();
    var cellsOfRow = "";
    var found = false;
    var compareWith = "";
    
    //Recorremos todas las filas con contenido en las tablas 
    for (var i = 1; i < tableReg.rows.length; i++) {
        cellsOfRow = tableReg.rows[i].getElementsByTagName('td');
        found = false;
        //recorremos todas la celdas
        for (var j = 0; j < cellsOfRow.length && !found; j++) {
            compareWith = cellsOfRow[j].innerHTML.toLowerCase();
            
            //Buscamos el texto en el contenido de la celda
            if(searchText.length == 0 || (compareWith.indexOf(searchText) > -1)){
                found = true;
            }
        }
        if(found){
            tableReg.rows[i].style.display = '';
        }else{
            tableReg.rows[i].style.display = 'none'; 
        }
    }
    
}