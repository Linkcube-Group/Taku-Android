#pragma strict

private var bSidesColliderFlag : boolean;

private var hInGameScript : InGameScript;
private var hPlayerFrontColliderScript : PlayerFrontColliderScript;
private var hControllerScript : ControllerScript;

function Start()
{
	bSidesColliderFlag = true;
	
	hInGameScript = GameObject.Find("Player").GetComponent(InGameScript) as InGameScript;
	hPlayerFrontColliderScript = GameObject.Find("PlayerFrontCollider").GetComponent(PlayerFrontColliderScript) as PlayerFrontColliderScript;
	hControllerScript = GameObject.Find("Player").GetComponent(ControllerScript) as ControllerScript;
}

function OnCollisionEnter(collision : Collision)
{	
	if (hInGameScript.isEnergyZero())
		return;
	else if (bSidesColliderFlag == true)
	{
		hPlayerFrontColliderScript.deactivateFrontCollider();
		hControllerScript.processStumble();
	}
}

public function isSidesColliderActive() { return bSidesColliderFlag; }
public function deactivateSidesCollider() { bSidesColliderFlag = false; }
public function activateSidesCollider() { bSidesColliderFlag = true; }