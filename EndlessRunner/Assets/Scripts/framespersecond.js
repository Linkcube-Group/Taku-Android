
private var updateInterval : float = 0.5;
private var accum : float = 0.0;
private var frames : int = 0;
private var timeleft : float;

private var FPS : float = 0.0;
private var FPS_Text_Ref : GUIText;

function Start()
{
    timeleft = updateInterval;
    FPS_Text_Ref = GetComponent(GUIText) as GUIText;
}

function Update()
{
    timeleft -= Time.deltaTime;
    accum += Time.timeScale/Time.deltaTime;
    ++frames;
    
    if( timeleft <= 0.0 )
    {
        FPS = (accum/frames);
        timeleft = updateInterval;
        accum = 0.0;
        frames = 0;
    }
    
}