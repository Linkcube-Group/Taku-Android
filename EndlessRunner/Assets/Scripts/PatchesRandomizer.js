#pragma strict


var patchesPrefabs : GameObject[];

private var goPreviousPatch : GameObject;
private var goCurrentPatch : GameObject;
private var goNextPatch : GameObject;
private var fPatchDistance : float = 3000.0;
private var tPlayer : Transform;

private var fPreviousTotalDistance : float = 0.0;
private var iCurrentPNum : int = 1;

private var hInGameScript : InGameScript;
private var hElementsGenerator : ElementsGenerator;
private var hCheckPointsMain : CheckPointsMain;

public function getCoveredDistance():float { return fPreviousTotalDistance; } 

function Start()
{
	iCurrentPNum = 1;	
	fPreviousTotalDistance = 0.0;
	
	hInGameScript = this.GetComponent(InGameScript) as InGameScript;
	hCheckPointsMain = GetComponent(CheckPointsMain) as CheckPointsMain;
	hElementsGenerator = this.GetComponent(ElementsGenerator) as ElementsGenerator;
	
	instantiateStartPatch();	
	goPreviousPatch = goCurrentPatch;	
	
	tPlayer = GameObject.Find("Player").transform;
	hCheckPointsMain.setChildGroups();
	
	hCheckPointsMain.SetCurrentPatchCPs();
	hCheckPointsMain.SetNextPatchCPs();
}

function Update()
{
	if(hInGameScript.isGamePaused()==true)
		return;
	
	if(tPlayer.position.x>(iCurrentPNum*fPatchDistance)+100.0)
	{
		Destroy(goPreviousPatch);
		iCurrentPNum++;
	}
}

public function createNewPatch()
{
	goPreviousPatch = goCurrentPatch;
	goCurrentPatch = goNextPatch;
	
	instantiateNextPatch();	
	hCheckPointsMain.setChildGroups();
	
	fPreviousTotalDistance += CheckPointsMain.fPathLength;
	
	hElementsGenerator.generateElements();	//generate obstacles on created patch
}

private function instantiateNextPatch()
{	
	goNextPatch = Instantiate(patchesPrefabs[Random.Range(0,patchesPrefabs.length)],Vector3(fPatchDistance*(iCurrentPNum+1),0,0),Quaternion());
}

private function instantiateStartPatch()
{
	goCurrentPatch = Instantiate(patchesPrefabs[Random.Range(0,patchesPrefabs.length)], Vector3(0,0,0),Quaternion());
	goNextPatch = Instantiate(patchesPrefabs[Random.Range(0,patchesPrefabs.length)],Vector3(fPatchDistance,0,0),Quaternion());
}

public function getCurrentPatch():GameObject { return goCurrentPatch; }
public function getNextPatch():GameObject { return goNextPatch; }