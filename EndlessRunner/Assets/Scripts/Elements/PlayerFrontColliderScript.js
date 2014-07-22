#pragma strict


private var bFrontColliderFlag : boolean;

private var hPlayerSidesColliderScript : PlayerSidesColliderScript;
private var hInGameScript : InGameScript;

function Start()
{
	bFrontColliderFlag = true;
	
	hPlayerSidesColliderScript = GameObject.Find("PlayerSidesCollider").GetComponent(PlayerSidesColliderScript) as PlayerSidesColliderScript;
	hInGameScript = GameObject.Find("Player").GetComponent(InGameScript) as InGameScript;
}

function OnCollisionEnter(collision : Collision)
{		
	if (bFrontColliderFlag == true)
	{
		hPlayerSidesColliderScript.deactivateSidesCollider();
		hInGameScript.collidedWithObstacle();
	}
}

public function isFrontColliderActive() { return bFrontColliderFlag; }
public function activateFrontCollider() { bFrontColliderFlag = true; }
public function deactivateFrontCollider() { bFrontColliderFlag = false; }