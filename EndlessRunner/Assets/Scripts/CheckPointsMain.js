#pragma strict

static var fPathLength : float = 0.0;
static var fNextPathLength : float = 0.0;
private var defaultPathLength : float = 3000.0;

private var CurrentAngle : float = 0.0;
private var CurrentDir : Vector3;
private var Current_MidPoint : Vector2;

private var CPPositions : Vector3[];
private var NextCPPositions : Vector3[];

private var WaypointAngle : float = 0.0;
private var CurrentPercent : float = 0.0;

private var goCPsGroup : GameObject;
private var goNextCPsGroup : GameObject;
private var tCPsGroup : Transform;
private var tNextCPsGroup : Transform;

private var hInGameScript : InGameScript;
private var hControllerScript : ControllerScript;
private var hPatchesRandomizer : PatchesRandomizer;
private var hElementsGenerator : ElementsGenerator;

private var PatchNumber : int = 0;

function Start()
{	
	WaypointAngle = 0.0;
	fPathLength = defaultPathLength;
	fNextPathLength = defaultPathLength;
	CurrentPercent = 0.0;
	
	hInGameScript = this.GetComponent(InGameScript) as InGameScript;
	hControllerScript = this.GetComponent(ControllerScript) as ControllerScript;
	hPatchesRandomizer = this.GetComponent(PatchesRandomizer) as PatchesRandomizer;
	hElementsGenerator = this.GetComponent(ElementsGenerator) as ElementsGenerator;
}

public function setChildGroups()
{
	for (var child:Transform in hPatchesRandomizer.getCurrentPatch().transform)
	{
		if(child.name.Contains("CheckPoints"))
			goCPsGroup = child.gameObject;
	}
	for (var child:Transform in hPatchesRandomizer.getNextPatch().transform)
	{
		if(child.name.Contains("CheckPoints"))
			goNextCPsGroup = child.gameObject;
	}
}

public function SetCurrentPatchCPs()
{	
	var i : int = 0;
	
	CurrentAngle = 90.0;
	
	tCPsGroup = goCPsGroup.transform;
	fPathLength = (tCPsGroup.GetComponent(PathLineDrawer) as PathLineDrawer).fPathLength;
	
	CPPositions = [];
	CPPositions = new Vector3[(tCPsGroup.GetComponent(PathLineDrawer) as PathLineDrawer).Parameterized_CPPositions.length];
	for(i=0;i<CPPositions.length;i++)
	{
		CPPositions[i] = (tCPsGroup.GetComponent(PathLineDrawer) as PathLineDrawer).Parameterized_CPPositions[i];
		CPPositions[i].x = CPPositions[i].x + PatchNumber * defaultPathLength;
	}
	
	PatchNumber++;
}

public function SetNextPatchCPs()
{
	var i : int = 0;
	
	tNextCPsGroup = goNextCPsGroup.transform;
	fNextPathLength = (tNextCPsGroup.GetComponent(PathLineDrawer) as PathLineDrawer).fPathLength;
	
	NextCPPositions = [];
	NextCPPositions = new Vector3[(tNextCPsGroup.GetComponent(PathLineDrawer) as PathLineDrawer).Parameterized_CPPositions.length];
	for(i=0;i<NextCPPositions.length;i++)
	{
		NextCPPositions[i] = (tNextCPsGroup.GetComponent(PathLineDrawer) as PathLineDrawer).Parameterized_CPPositions[i];
		NextCPPositions[i].x = NextCPPositions[i].x + PatchNumber * defaultPathLength;
	}
}

private function Interp(pts : Vector3[] , t : float) : Vector3
{
	t = Mathf.Clamp(t,0.0,2.0);
	var numSections : int  = pts.Length - 3;
	var currPt : int  = Mathf.Min(Mathf.FloorToInt(t * parseFloat(numSections)), numSections - 1);
	var u : float = t * parseFloat(numSections) - parseFloat(currPt);
	var a : Vector3 = pts[(currPt)%pts.Length];
	var b : Vector3 = pts[(currPt + 1)%pts.Length];
	var c : Vector3 = pts[(currPt + 2)%pts.Length];
	var d : Vector3 = pts[(currPt + 3)%pts.Length];
	
	return .5f * (
		(-a + 3f * b - 3f * c + d) * (u * u * u)
		+ (2f * a - 5f * b + 4f * c - d) * (u * u)
		+ (-a + c) * u
		+ 2f * b
	);
}

public function SetNextMidPointandRotation(CurrentDistanceOnPath : float, CurrentForwardSpeed : float) : float
{
	CurrentPercent = (CurrentDistanceOnPath+CurrentForwardSpeed)/fPathLength;
	
	if(CurrentPercent>=1.0)
	{
		var PreviousPathLength : float = fPathLength;
		(GameObject.Find("Player").GetComponent(PatchesRandomizer) as PatchesRandomizer).createNewPatch();
		
		CurrentDistanceOnPath = (CurrentDistanceOnPath+CurrentForwardSpeed) - PreviousPathLength;
		CurrentDistanceOnPath = Mathf.Abs(CurrentDistanceOnPath);
		hControllerScript.setCurrentDistanceOnPath(CurrentDistanceOnPath);
		SetCurrentPatchCPs();
		SetNextPatchCPs();
		
		CurrentPercent = (CurrentDistanceOnPath+CurrentForwardSpeed)/fPathLength;
	}
	
	var MidPointVector3 : Vector3 = Interp(CPPositions,CurrentPercent);

	Current_MidPoint.x = MidPointVector3.x;
	Current_MidPoint.y = MidPointVector3.z;
	
	var ForwardPointVector3 : Vector3 = Interp(CPPositions,CurrentPercent+0.001);
	var BackPointVector3 : Vector3 = Interp(CPPositions,CurrentPercent-0.001);
	CurrentDir = ForwardPointVector3 - BackPointVector3;
	CurrentAngle = PosAngleofVector(CurrentDir);
	if(CurrentAngle>180.0)
		CurrentAngle = CurrentAngle-360.0;
		
	return (CurrentDistanceOnPath+CurrentForwardSpeed);
}

private function PosAngleofVector(InputVector : Vector3)
{
	var AngleofInputVector : float = 57.3 * (Mathf.Atan2(InputVector.z,InputVector.x));
	if(AngleofInputVector<0.0)
		AngleofInputVector = AngleofInputVector + 360.0;
	return AngleofInputVector;
}

public function getCurrentAngle():float
{
	return CurrentAngle;
}

public function getCurrentWSPointBasedOnPercent(Percent_Val: float):Vector3
{
	var NextSideDir : Vector3 = Vector3(0,0,1);
	
	var ForwardPointVector3 : Vector3 = Interp(CPPositions,(Percent_Val+0.001));
	var BackPointVector3 : Vector3 = Interp(CPPositions,(Percent_Val-0.001));
	var NextCurrentDir : Vector3 = ForwardPointVector3 - BackPointVector3;
	NextSideDir = RotateY0Vector(NextCurrentDir, 90.0);
	NextSideDir.Normalize();
	WaypointAngle = PosAngleofVector(NextCurrentDir);
	if(WaypointAngle>180.0)
		WaypointAngle = WaypointAngle-360.0;
	
	return Interp(CPPositions,Percent_Val);
}

public function getNextWSPointBasedOnPercent(Percent_Val: float):Vector3
{
	var NextSideDir : Vector3 = Vector3(0,0,1);
	
	var ForwardPointVector3 : Vector3 = Interp(NextCPPositions,(Percent_Val+0.001));
	var BackPointVector3 : Vector3 = Interp(NextCPPositions,(Percent_Val-0.001));
	var NextCurrentDir : Vector3 = ForwardPointVector3 - BackPointVector3;
	NextSideDir = RotateY0Vector(NextCurrentDir, 90.0);
	NextSideDir.Normalize();
	WaypointAngle = PosAngleofVector(NextCurrentDir);
	if(WaypointAngle>180.0)
		WaypointAngle = WaypointAngle-360.0;
	
	return Interp(NextCPPositions,Percent_Val);
}

public function getWaypointAngle():float
{
	return WaypointAngle;
}

public function getCurrentDirection():Vector3 { return CurrentDir; }

public function getCurrentMidPoint():Vector2 { return Current_MidPoint; }

private function RotateY0Vector(inputVector : Vector3, angletoRotate : float)
{
	var FinalVector : Vector3 = Vector3.zero;
	angletoRotate = angletoRotate/57.3;
	FinalVector.x = Mathf.Cos(angletoRotate) * inputVector.x - Mathf.Sin(angletoRotate) * inputVector.z;
	FinalVector.z = Mathf.Sin(angletoRotate) * inputVector.x + Mathf.Cos(angletoRotate) * inputVector.z;
	
	return FinalVector;
}