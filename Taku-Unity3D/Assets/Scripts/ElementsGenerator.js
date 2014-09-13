#pragma strict
import System.IO;

public enum ElementType { SingleLaneObstacle = 0, MultiLaneObstacle = 1, Currency = 3, Powerup = 4, Null = 5 }

private class Element extends System.ValueType
{
	var tPrefabHandle : Transform[];
	var iFrequency : int;
	var elementType : ElementType;
	var iPrefabHandleIndex : int;
}

var obstaclePrefabs : GameObject[];
var powerupPrefabs : GameObject[];
var currencyPrefab : GameObject;
private var elements : Element[];
private var tPrefabHandlesParent : Transform;

private var iObstacleCount : int;
private var iPowerupCount : int;
private var iTotalCount : int;

private var hPowerupsMainController : PowerupsMainController;
private var hCheckPointsMain : CheckPointsMain;

private var fDefaultIncrement : float = 0.2f;//0.05;
private var iLastPowerupGenerated : int = 0;
private var iForcePowerupGeneration : int = 2;
private var bPowerupPlaced : boolean = false;

private var bGenerateElements : boolean;
public function generateElements():void
{
	bGenerateElements = true;
}

function Start()
{
	iObstacleCount = obstaclePrefabs.Length;
	iPowerupCount = powerupPrefabs.Length;
	iTotalCount = iObstacleCount + iPowerupCount + 1;
	
	bGenerateElements = true;
	bPowerupPlaced = true;
	var dt : System.DateTime = System.DateTime.Now;
	Random.seed = dt.Hour * dt.Minute * dt.Second;
	
	setPrefabHandlers();
	
	hPowerupsMainController = this.GetComponent(PowerupsMainController) as PowerupsMainController;
	hCheckPointsMain = GameObject.Find("Player").GetComponent(CheckPointsMain) as CheckPointsMain;
	
	var i : float = 0.20;
	while (i < 0.99)
	{	
		var incrementValue = generateElements(getRandomElement(), i, true);
		i += incrementValue;
	}
}

function Update()
{
	var i : float = 0.0;
	if (bGenerateElements)
	{
		bGenerateElements = false;
		bPowerupPlaced = false;
		iLastPowerupGenerated++;
		
		var fLocation : float;
		while (i < 0.99)
		{			
		    fLocation = i;
		    var incrementValue = generateElements(getRandomElement(), fLocation, false);
			i += incrementValue;
		}
	}
}

private function generateElements(elementNumber:int, fLocation:float, bStartPatch:boolean)
{	
	var v3Position : Vector3;
	var hitInfo : RaycastHit;
	var CurrentAngle : float;
	var fDefaultDisplacementValue : float = 15;
	var fDisplacement : float = fDefaultDisplacementValue;
	
	if (elements[elementNumber].elementType == ElementType.SingleLaneObstacle)
	{		
	    v3Position = getPosition(fLocation + fDefaultIncrement, bStartPatch);
	    hitInfo = getHitInfo(v3Position);
	    //v3Position.z += fDisplacement;
	    v3Position.y = hitInfo.point.y;
	    CurrentAngle = -hCheckPointsMain.getWaypointAngle();
	    instantiateElement(elementNumber, v3Position, CurrentAngle, hitInfo.normal);

	    var iLeftRight = Random.Range(0,2);
	    if(iLeftRight == 0){
	        v3Position.z += Mathf.Abs(fDefaultDisplacementValue);
	    }else{
	        v3Position.z -= Mathf.Abs(fDefaultDisplacementValue);
	    }
	    v3Position.y = hitInfo.point.y;
	    CurrentAngle = -hCheckPointsMain.getWaypointAngle();
	    instantiateElement(elementNumber, v3Position, CurrentAngle, hitInfo.normal);

	    fLocation = fDefaultIncrement*2;
		for (var i:int = 0; i<1/*Random.Range(1,9)*/; i++)
		{
		//	//pick where to generate obstacle horizontally on path
		//	var iLane = Random.Range(0,3);
		//	if (iLane == 0)
		//		fDisplacement = Mathf.Abs(fDefaultDisplacementValue);
		//	else if (iLane == 1)
		//		fDisplacement = -Mathf.Abs(fDefaultDisplacementValue);
		//	else
		//		fDisplacement = 0;
			
		//	//pick where to generate obstacle vertically on path
		//	var iVerticalPosition = Random.Range(0,3);
		//	if (iVerticalPosition == 0)
		//	{
		//		v3Position = getPosition(fLocation+fDefaultIncrement, bStartPatch);
		//		hitInfo = getHitInfo(v3Position);
		//	}
		//	else if (iVerticalPosition == 1)
		//	{
		//	    v3Position = getPosition(fLocation+(fDefaultIncrement*2), bStartPatch);
		//	    hitInfo = getHitInfo(v3Position);
		//	}
		//	else
		//	{
		//		v3Position = getPosition(fLocation, bStartPatch);
		//		hitInfo = getHitInfo(v3Position);		
		//	}
			
		//	if (fLocation >= 1.0)//dont create obstacles on next patch
		//		continue;
							
		//	v3Position.z += fDisplacement;
		//	v3Position.y = hitInfo.point.y;
		//	CurrentAngle = -hCheckPointsMain.getWaypointAngle();
		//	instantiateElement(elementNumber, v3Position, CurrentAngle, hitInfo.normal);
		}		
		
        //fLocation = fDefaultIncrement*2;
	}
	else if (elements[elementNumber].elementType == ElementType.Currency)
	{
		var fObstacleDisplacement : float[] = new float[2];
		
		var iCurrencyLane = Random.Range(0,3);
		if (iCurrencyLane == 0)
		{
			fDisplacement = Mathf.Abs(fDefaultDisplacementValue);
			fObstacleDisplacement[0] = 0;			
			fObstacleDisplacement[1] = -Mathf.Abs(fDefaultDisplacementValue);
		}
		else if (iCurrencyLane == 1)
		{
			fDisplacement = -Mathf.Abs(fDefaultDisplacementValue);
			fObstacleDisplacement[0] = Mathf.Abs(fDefaultDisplacementValue);
			fObstacleDisplacement[1] = 0;
		}
		else
		{
			fDisplacement = 0;
			fObstacleDisplacement[0] = -Mathf.Abs(fDefaultDisplacementValue);			
			fObstacleDisplacement[1] = Mathf.Abs(fDefaultDisplacementValue);
		}
		
		var iToGenerate:int = 1;//Random.Range(3,7);
		for (i=0; i<iToGenerate; i++)
		{
			v3Position = getPosition(fLocation, bStartPatch);
			v3Position.z += fDisplacement;
			hitInfo = getHitInfo(v3Position);
			v3Position.y = hitInfo.point.y;
			CurrentAngle = -hCheckPointsMain.getWaypointAngle();			
			instantiateElement(elementNumber, v3Position, CurrentAngle, hitInfo.normal);
			
			var parallelElement = getRandomElement();
			if (elements[parallelElement].elementType != ElementType.MultiLaneObstacle)
			{
				v3Position = getPosition(fLocation, bStartPatch);
				v3Position.z += fObstacleDisplacement[Random.Range(0,fObstacleDisplacement.Length)];
				hitInfo = getHitInfo(v3Position);
				v3Position.y = hitInfo.point.y;
				CurrentAngle = -hCheckPointsMain.getWaypointAngle();
				instantiateElement(parallelElement, v3Position, CurrentAngle, hitInfo.normal);
			}
			
			fLocation += 0.05;
			if (fLocation >= 1.0)
				break;
		}
		
		fLocation = iToGenerate*0.05;
	}
	else if (elements[elementNumber].elementType == ElementType.MultiLaneObstacle)
	{
		v3Position = getPosition(fLocation, bStartPatch);
		hitInfo = getHitInfo(v3Position);		
		v3Position.y = hitInfo.point.y;
		CurrentAngle = -hCheckPointsMain.getWaypointAngle();
		instantiateElement(elementNumber, v3Position, CurrentAngle, hitInfo.normal);
		
		fLocation = 0.05;
	}
	
	return fLocation;
}

private function getPosition(fLocation:float, bStartPatch:boolean):Vector3
{
	if (bStartPatch == true)	
		return hCheckPointsMain.getCurrentWSPointBasedOnPercent(fLocation);
	else
		return hCheckPointsMain.getNextWSPointBasedOnPercent(fLocation);
}

private function getHitInfo(v3Position:Vector3):RaycastHit
{
	var Groundhit : boolean = false;
	var hitInfo : RaycastHit;
	var DownPos : Vector3 = Vector3(0,-100,0) + v3Position;
	var layerMask = 1<<9;
	Groundhit = Physics.Linecast(v3Position + Vector3(0,100,0),DownPos,hitInfo,layerMask);
	
	return hitInfo;
}

private function instantiateElement(elementNumber:int, v3Position:Vector3, CurrentAngle:float, hitInfoNormal:Vector3):void
{
	if (elementNumber < 0)
		return;
	
	var ObjectHandle : Transform;
	
	if (elementNumber < iObstacleCount)//obstacles
	{
		ObjectHandle = elements[elementNumber].tPrefabHandle[elements[elementNumber].iPrefabHandleIndex];		
		elements[elementNumber].iPrefabHandleIndex++;
		if (elements[elementNumber].iPrefabHandleIndex >= elements[elementNumber].tPrefabHandle.Length)
			elements[elementNumber].iPrefabHandleIndex = 0;
		ObjectHandle.gameObject.SetActive(true);
		ObjectHandle.up = hitInfoNormal;
		ObjectHandle.position = v3Position;
		ObjectHandle.localEulerAngles = Vector3(0,0,0);
		ObjectHandle.Rotate(0,CurrentAngle,0);
	}
	else if (elementNumber >= iObstacleCount && elementNumber < (iObstacleCount+iPowerupCount))//powerups
	{
	    elementNumber = (iObstacleCount+iPowerupCount);
	    ObjectHandle = elements[elementNumber].tPrefabHandle[elements[elementNumber].iPrefabHandleIndex];
	    elements[elementNumber].iPrefabHandleIndex++;
	    if (elements[elementNumber].iPrefabHandleIndex >= elements[elementNumber].tPrefabHandle.Length)
	        elements[elementNumber].iPrefabHandleIndex = 0;
	    ObjectHandle.gameObject.SetActive(true);
	    ObjectHandle.up = hitInfoNormal;
	    ObjectHandle.position = v3Position;
	    ObjectHandle.localEulerAngles = Vector3(0,0,0);
	    ObjectHandle.Rotate(0,CurrentAngle,0);
	    (ObjectHandle.GetComponent(PowerupScript) as PowerupScript).initPowerupScript();

		//ObjectHandle = elements[elementNumber].tPrefabHandle[elements[elementNumber].iPrefabHandleIndex];
		//elements[elementNumber].iPrefabHandleIndex++;
		//if (elements[elementNumber].iPrefabHandleIndex >= elements[elementNumber].tPrefabHandle.Length)
		//	elements[elementNumber].iPrefabHandleIndex = 0;
		//ObjectHandle.gameObject.SetActive(true);
		//ObjectHandle.up = hitInfoNormal;
		//ObjectHandle.position = v3Position;
		//ObjectHandle.localEulerAngles = Vector3(0,0,0);
		//ObjectHandle.Rotate(0,CurrentAngle,0);
		//(ObjectHandle.GetComponent(PowerupScript) as PowerupScript).initPowerupScript();
	}
	else if (elementNumber == (iObstacleCount+iPowerupCount))//currency
	{
		ObjectHandle = elements[elementNumber].tPrefabHandle[elements[elementNumber].iPrefabHandleIndex];
		elements[elementNumber].iPrefabHandleIndex++;
		if (elements[elementNumber].iPrefabHandleIndex >= elements[elementNumber].tPrefabHandle.Length)
			elements[elementNumber].iPrefabHandleIndex = 0;
		ObjectHandle.gameObject.SetActive(true);
		ObjectHandle.up = hitInfoNormal;
		ObjectHandle.position = v3Position;
		ObjectHandle.localEulerAngles = Vector3(0,0,0);
		ObjectHandle.Rotate(0,CurrentAngle,0);
		(ObjectHandle.GetComponent(PowerupScript) as PowerupScript).initPowerupScript();
	}
}

private function getRandomElement():int
{
	var highestFrequency:float = 0;
	var elementIndex : int = 0;
	
	var i : int = 0;
	var tempFreq : float = 0;
	
	if (iLastPowerupGenerated > iForcePowerupGeneration
	&& !hPowerupsMainController.isPowerupActive()
	&& bPowerupPlaced == false)
	{
		for (i= iObstacleCount; i<(iObstacleCount+iPowerupCount); i++)
		{
			tempFreq = elements[i].iFrequency * Random.value;
			if (highestFrequency < tempFreq)
			{
				highestFrequency = tempFreq;
				elementIndex = i;
			}
		}
		iLastPowerupGenerated = 0;	
	}
	else
	{
		for (i=0; i<iTotalCount; i++)
		{	
			if ( (elements[i].elementType == ElementType.Powerup 
			&& hPowerupsMainController.isPowerupActive() )
			|| (elements[i].elementType == ElementType.Powerup && bPowerupPlaced == true) )
				continue;
			
			tempFreq = elements[i].iFrequency * Random.value;
			if (highestFrequency < tempFreq)
			{
				highestFrequency = tempFreq;
				elementIndex = i;
			}
		}
	}
	
	if (elements[elementIndex].elementType == ElementType.Powerup)
		bPowerupPlaced = true;
	
	return elementIndex;
}

private function setPrefabHandlers()
{
	tPrefabHandlesParent = GameObject.Find("PFHandlesGroup").transform;
	elements = new Element[iTotalCount];
	
	for (var i=0; i< iObstacleCount; i++)
	{			
		elements[i].iFrequency = (obstaclePrefabs[i].GetComponent(ObstacleScript) as ObstacleScript).frequency;
		elements[i].tPrefabHandle = new Transform[elements[i].iFrequency*4 + 1];
		elements[i].iPrefabHandleIndex = 0;
		elements[i].elementType = (obstaclePrefabs[i].GetComponent(ObstacleScript) as ObstacleScript).obstacleAreaType;
		
		for (var j=0; j<elements[i].tPrefabHandle.Length; j++)
		{
			elements[i].tPrefabHandle[j] = Instantiate(obstaclePrefabs[i],Vector3(-100,0,0),Quaternion()).transform;
			elements[i].tPrefabHandle[j].parent = tPrefabHandlesParent;
		}		
	}
	
	var index : int = 0;
	for (i=iObstacleCount; i<(iPowerupCount+iObstacleCount); i++)
	{		
		elements[i].iFrequency = (powerupPrefabs[index].GetComponent(PowerupScript) as PowerupScript).frequency;
		elements[i].tPrefabHandle = new Transform[elements[i].iFrequency*1 + 1];
		elements[i].iPrefabHandleIndex = 0;
		elements[i].elementType = ElementType.Powerup;
		
		for (j=0; j<elements[i].tPrefabHandle.Length; j++)
		{
			elements[i].tPrefabHandle[j] = Instantiate(powerupPrefabs[index],Vector3(-100,0,0),Quaternion()).transform;
			elements[i].tPrefabHandle[j].parent = tPrefabHandlesParent;
		}
		
		++index;		
	}
	
	index = iPowerupCount+iObstacleCount;
	elements[index].iFrequency = (currencyPrefab.GetComponent(PowerupScript) as PowerupScript).frequency;
	elements[index].tPrefabHandle = new Transform[elements[i].iFrequency*11 + 1];
	elements[index].iPrefabHandleIndex = 0;
	elements[index].elementType = ElementType.Currency;
	for (j=0; j<elements[index].tPrefabHandle.Length; j++)
	{
		elements[index].tPrefabHandle[j] = Instantiate(currencyPrefab,Vector3(-100,0,0),Quaternion()).transform;
		elements[index].tPrefabHandle[j].parent = tPrefabHandlesParent;
	}	
}