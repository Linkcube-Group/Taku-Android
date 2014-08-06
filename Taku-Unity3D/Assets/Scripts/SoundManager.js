#pragma strict

var asCharacterSounds : AudioSource[];
var asPowerupSounds : AudioSource[];
var asMenuSounds : AudioSource[];
var asMusic : AudioSource[];
var asEnemySounds : AudioSource[];

//Sound Enums
enum CharacterSounds
{
	Footsteps = 0,
	JumpLand = 1
}

enum PowerupSounds
{
	CurrencyCollection = 0,
	PowerupCollection = 1
}

enum MenuSounds
{
	ButtonTap = 0
}

enum EnemySounds
{
	TiresSqueal = 0,
	Siren = 1
}

enum BackgroundSounds
{
	Background = 0
}

//Constants
private var bSoundEnabled : boolean = true;	//gameplay sounds

private var hInGameScript : InGameScript;
private var hControllerScript  : ControllerScript;

private var bPlayFootsteps  : boolean = false;
private var bFootstepsPlaying : boolean = false;

public function isSoundEnabled():boolean { return bSoundEnabled; }
public function toggleSoundEnabled(flag:boolean) { bSoundEnabled = (flag ? true : false); }

function Start()
{
	hControllerScript = GameObject.Find("Player").GetComponent(ControllerScript) as ControllerScript;
	hInGameScript = GameObject.Find("Player").GetComponent(InGameScript) as InGameScript;
	hControllerScript = GameObject.Find("Player").GetComponent(ControllerScript) as ControllerScript;
	
	stopAllSounds();
}

function Update ()
{
	StartCoroutine(toggleFootStepsSound());

	if(hInGameScript.isGamePaused()==true)
		stopSound(CharacterSounds.Footsteps);
	
	if(bPlayFootsteps==true)
	{
		//adjust footsteps pitch according to movement speed
		asCharacterSounds[CharacterSounds.Footsteps].pitch = hControllerScript.getCurrentForwardSpeed()/3.0;
		if(bFootstepsPlaying==false)
		{
			if (bSoundEnabled)
				asCharacterSounds[CharacterSounds.Footsteps].Play();
			bFootstepsPlaying = true;
		}
	}
	else
	{
		if(bFootstepsPlaying==true)
		{			
			if (bSoundEnabled)
				asCharacterSounds[CharacterSounds.Footsteps].Stop();
			bFootstepsPlaying = false;
		}
	}
}

public function playSound(soundType : CharacterSounds)
{
	if (bSoundEnabled)
		asCharacterSounds[soundType].Play();
}
public function playSound(soundType : PowerupSounds)
{
	if (bSoundEnabled)
		asPowerupSounds[soundType].Play();
}
public function playSound(soundType : MenuSounds)
{
	if (bSoundEnabled)
		asMenuSounds[soundType].Play();
}
public function playSound(soundType : EnemySounds)
{
	if (bSoundEnabled && asEnemySounds[soundType].isPlaying == false)
		asEnemySounds[soundType].Play();
}

public function playSound(soundType : BackgroundSounds)
{
	if (bSoundEnabled && asMusic[soundType].isPlaying == false)
		asMusic[soundType].Play();
}

public function stopSound(soundType : CharacterSounds)
{
	asCharacterSounds[soundType].Stop();
}
public function stopSound(soundType : PowerupSounds)
{
	asPowerupSounds[soundType].Stop();
}
public function stopSound(soundType : MenuSounds)
{
	asMenuSounds[soundType].Stop();
}
public function stopSound(soundType : EnemySounds)
{
	asEnemySounds[soundType].Stop();
}

public function stopSound(soundType : BackgroundSounds)
{
	asMusic[soundType].Stop();
}

private function toggleFootStepsSound()
{	
	yield WaitForEndOfFrame();
	
	if(!hControllerScript.isInAir())
		bPlayFootsteps = true;
	else
		bPlayFootsteps = false;
}

public function stopAllSounds()
{	
	for (var i=0; i<CharacterSounds.GetValues(CharacterSounds).Length; i++)
		asCharacterSounds[i].Stop();
	for (i=0; i<PowerupSounds.GetValues(PowerupSounds).Length; i++)
		asPowerupSounds[i].Stop();
	for (i=0; i<MenuSounds.GetValues(MenuSounds).Length; i++)
		asMenuSounds[i].Stop();
	for (i=0; i<EnemySounds.GetValues(EnemySounds).Length; i++)
		asEnemySounds[i].Stop();
	
	bFootstepsPlaying = false;
}

public function isPlaying(sound:CharacterSounds):boolean
{
	if (asCharacterSounds[sound].isPlaying)
		return true;
	else
		return false;
}

public function isPlaying(sound:PowerupSounds):boolean
{
	if (asPowerupSounds[sound].isPlaying)
		return true;
	else
		return false;
}

public function isPlaying(sound:MenuSounds):boolean
{
	if (asMenuSounds[sound].isPlaying)
		return true;
	else
		return false;
}

public function isPlaying(sound:EnemySounds):boolean
{
	if (asEnemySounds[sound].isPlaying)
		return true;
	else
		return false;
}