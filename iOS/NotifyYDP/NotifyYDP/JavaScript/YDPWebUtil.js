
function getYDPUserName()
{
    var userName = document.getElementById('TitleContent_TitleContent_lblProviderName');
    return userName.childNodes[0].textContent;
    
}

function getCarePlan()
{
    var carePlanTable = document.getElementById('ContentPlaceHolder_MainContent_MainContent_CarePlanGridView');
    var result = "NO";
    if(carePlanTable)
    {
        result = "";
        var carePlanTable = document.getElementById('ContentPlaceHolder_MainContent_MainContent_CarePlanGridView');
        if(carePlanTable)
        {
            var carePlan = carePlanTable.getElementsByTagName('tr');
            for(var i = 1; i < carePlan.length; i++)
            {
                var carePlanRow = carePlan[i].getElementsByTagName('td');
                for(var j = 0; j < carePlanRow.length; j++)
                {
                    var childValue = carePlanRow[j].childNodes[1].textContent;
                    if(childValue)
                    {
                        
                        result += childValue;
                        result += ":$#";
                    }
                    else
                    {
                        
                        result += carePlanRow[j].childNodes[1].src;
                        result += ":$#";
                    }
                }
            }
        }
    }

	return result;
}

function getAllergies()
{
    var allergiesTable = document.getElementById('ContentPlaceHolder_MainContent_MainContent_DataList1');
    var allergy = allergiesTable.getElementsByTagName('tr');
    var result = "";
    
    for(var i = 1; i < allergy.length; i +=3)
    {
        var allergyRow = allergy[i].getElementsByTagName('td');
        for(var j = 0; j < 4; j++)
        {
            var childValue = allergyRow[j].childNodes[1].textContent;
            childValue += " ";
            result += childValue;
            result += ":$#";
        }
    }
	return result;
    
}



