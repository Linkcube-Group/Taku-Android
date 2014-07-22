#pragma strict

public enum PowerUps
{	
	Magnetism = 0,
	Currency = 1
}

private var tPlayer : Transform;

private var iCurrencyUnits : int = 0;
private var fMangetismRadius : float;
private var fMagnetismDefaultRadius : float;
private var iPowerupCount : int;

private var bPowerupStatus : boolean[];
private var fPowerupStartTime : float[];
private var fPowerupTotalDuration : float[];

private var hInGameScript : InGameScript;
private var hSoundManager : SoundManager;
private var hControllerScript : ControllerScript;
private var hPreyScript : PreyScript;

private var tHUDPUMeter : Transform;
private var tHUDPUMeterBar : Transform;

function Start()
{
	tPlayer = transform;	
	
	tHUDPUMeter = GameObject.Find("HUDMainGroup/HUDPUMeter").GetComponent(Transform) as Transform;
	tHUDPUMeterBar = GameObject.Find("HUDMainGroup/HUDPUMeter/HUD_PU_Meter_Bar_Parent").GetComponent(Transform) as Transform;
	
	iPowerupCount = PowerUps.GetValues(PowerUps).Length-1;
	
	bPowerupStatus = new boolean[iPowerupCount];
	fPowerupStartTime = new float[iPowerupCount];	
	fPowerupTotalDuration = new float[iPowerupCount];

	hInGameScript = this.GetComponent(InGameScript) as InGameScript;
	hControllerScript = this.GetComponent(ControllerScript) as ControllerScript;
	hSoundManager = GameObject.Find("SoundManager").GetComponent(SoundManager) as SoundManager;
	hPreyScript = GameObject.Find("Prey").GetComponent(PreyScript) as PreyScript;
	
	tHUDPUMeter.transform.position.y -= 100;
	fMagnetismDefaultRadius = 200;
	fMangetismRadius = 200;
	iCurrencyUnits = 0;
	
	for(var i = 0; i <iPowerupCount ; i++)
	{
		bPowerupStatus[i] = false;
		fPowerupTotalDuration[i] = 10.0;
	}
}

function FixedUpdate ()
{	
	if(hInGameScript.isGamePaused()==true)
	{
		for (var j=0; j<iPowerupCount; j++)
		{
			if (bPowerupStatus[j] == true)
				fPowerupStartTime[j] += Time.deltaTime;
		}
		return;
	}

	for(var i : int = 0; i < iPowerupCount; i++)
	{
		if(bPowerupStatus[i]==true)
		{
			PowerupHUDVisual( (Time.time - fPowerupStartTime[i]), fPowerupTotalDuration[i] );
			
			if(Time.time - fPowerupStartTime[i]>=fPowerupTotalDuration[i])
			{
				deactivatePowerup(i);
			}
		}
	}
}

public function collectedPowerup(index : int)
{
	if(index==PowerUps.Currency)
	{
	    hPreyScript.AddScore(5);
		iCurrencyUnits += 1;
		hSoundManager.playSound(PowerupSounds.CurrencyCollection);

		return;
	}
	
	fPowerupStartTime[index] = Time.time;
	activatePowerUp(index);
}

private function activatePowerUp(index : int)
{
	tHUDPUMeter.transform.position.y = -88.6;
	bPowerupStatus[index] = true;
		
	if(index == PowerUps.Magnetism)
	{
		fMangetismRadius =  fMagnetismDefaultRadius + 2300;
	}
}

public function deactivatePowerup(index : int)
{	
	tHUDPUMeter.transform.position.y = 5000;
	bPowerupStatus[index] = false;
	
	if(index == PowerUps.Magnetism)
	{
		fMangetismRadius = fMagnetismDefaultRadius;
	}	
}

public function deactivateAllPowerups()
{
	for (var i=0; i< (PowerUps.GetValues(PowerUps).Length-2); i++)
	{
		if (bPowerupStatus[i] == true)
			deactivatePowerup(i);
	}
}

private function PowerupHUDVisual(fCurrentTime:float,fTotalTime:float)
{
	var iBarLength : float = tHUDPUMeterBar.transform.localScale.x;
	
	if (fCurrentTime <= 0)
		return;
	
	iBarLength = (fTotalTime-fCurrentTime)/fTotalTime;
	tHUDPUMeterBar.transform.localScale.x = iBarLength;
}

public function getMagnetismRadius() { return fMangetismRadius; }

public function getCurrencyUnits() { return iCurrencyUnits; }

public function isPowerupActive():boolean
{
	for (var i=0; i<iPowerupCount; i++)
	{
		if (bPowerupStatus[i] == true)
			return true;
	}
	
	return false;
}

public function isPowerupActive(ePUType:PowerUps):boolean
{
	return bPowerupStatus[ePUType];
}