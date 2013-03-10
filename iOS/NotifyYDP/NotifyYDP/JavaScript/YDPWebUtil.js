

function getCarePlan()
{
    
    var carePlanTable = document.getElementById('ContentPlaceHolder_MainContent_MainContent_CarePlanGridView');
    var result = "NO";
    if(carePlanTable){
        result = "";
        var carePlanTable = document.getElementById('ContentPlaceHolder_MainContent_MainContent_CarePlanGridView');
        if(carePlanTable){
            var carePlan = carePlanTable.getElementsByTagName('tr');
            for(var i = 1; i < carePlan.length; i++){
                var carePlanRow = carePlan[i].getElementsByTagName('td');
                for(var j = 0; j < carePlanRow.length; j++){
                    var childValue = carePlanRow[j].childNodes[1].textContent;
                    if(childValue){
                        
                        result += childValue;
                        result += ":$#";
                        console.log(childValue);
                    }
                    else{
                        
                        result += carePlanRow[j].childNodes[1].src;
                        result += ":$#";
                        console.log(carePlanRow[j].childNodes[1].src);
                    }
                }
            }
        }
    }

	return result;
}