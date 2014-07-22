#pragma strict

var powerupType : PowerUps;
var frequency : int;

private var tPlayer : Transform;
private var PUState : int = 0;
private var StartTime : float = 0.0;

//script references
private var hInGameScript : InGameScript;
private var hPowerupsMainController : PowerupsMainController;

private var v3StartPosition : Vector3;
private var bDestroyWhenFarFlag : boolean = false;
private var v3DistanceVector : Vector3;
private var fCatchRadius : float = 200;
private var v3CurrencyLerpPosition : Vector3;
private var hPreyScript : PreyScript;

function initPowerupScript()
{
	PUState = 0;
	bDestroyWhenFarFlag = false;
	transform.localScale = Vector3(1,1,1);
	StartTime = 0.0;
	v3DistanceVector = Vector3(0,0,0);
	
	toggleMeshRender(true);
}

function Start()
{
	tPlayer = GameObject.Find("Player").transform;
	
	hInGameScript = GameObject.Find("Player").GetComponent(InGameScript) as InGameScript;
	hPowerupsMainController = GameObject.Find("Player").GetComponent(PowerupsMainController) as PowerupsMainController;
	hPreyScript = GameObject.Find("Prey").GetComponent(PreyScript) as PreyScript;

}

function Update ()
{
	if(hInGameScript.isGamePaused()==true)
		return;
		
	if(PUState==1)
	{
		if (hPowerupsMainController.isPowerupActive(PowerUps.Magnetism) == true)
		{
			v3CurrencyLerpPosition = tPlayer.position;
			v3CurrencyLerpPosition.x += 2;
			v3CurrencyLerpPosition.y += 5;
			
			transform.position = Vector3.Lerp(transform.position,v3CurrencyLerpPosition,(Time.time-StartTime)/0.8);
			transform.localScale = Vector3.Lerp(transform.localScale,Vector3(0.1,0.1,0.1),(Time.time-StartTime)/0.8);
		}
		else
		{			
			transform.position = Vector3.Lerp(transform.position,tPlayer.position,(Time.time-StartTime)/0.2);
			transform.localScale = Vector3.Lerp(transform.localScale,Vector3(0.01,0.01,0.01),(Time.time-StartTime)/0.002);
		}
		
		if((Time.time - StartTime)>0.2)
		{
			if (powerupType == PowerUps.Currency || hPowerupsMainController.isPowerupActive(PowerUps.Magnetism) == true)			
				toggleMeshRender(false);		
			else			
				this.gameObject.SetActive(false);		
		}
		return;
	}
	
	v3DistanceVector = transform.position - tPlayer.position;
	
	if(v3DistanceVector.sqrMagnitude<40000.0)	
		bDestroyWhenFarFlag = true;
	
	if(bDestroyWhenFarFlag==true)
		if(v3DistanceVector.sqrMagnitude>90000.0)
		{
			if (powerupType == PowerUps.Currency)			
				toggleMeshRender(false);			
			else
				this.gameObject.SetActive(false);
		}

	if(powerupType==PowerUps.Currency)	
		fCatchRadius = hPowerupsMainController.getMagnetismRadius();
		
	if(v3DistanceVector.sqrMagnitude<fCatchRadius)
	{
		PUState = 1;
		StartTime = Time.time;
		
		hPowerupsMainController.collectedPowerup(powerupType);		
	}
}

private function toggleMeshRender(bState:boolean)
{
	if (powerupType == PowerUps.Currency)
	{
		(this.transform.Find("A_Crystal").GetComponent(MeshRenderer) as MeshRenderer).enabled = bState;
		(this.transform.Find("Shadow").GetComponent(MeshRenderer) as MeshRenderer).enabled = bState;
	}
	else if (powerupType == PowerUps.Magnetism)
	{		
		(this.transform.Find("Center").GetComponent(MeshRenderer) as MeshRenderer).enabled = bState;
	}
}