package nz.ac.vuw.ecs.swen225.gp22.domain;


/**
 * Chap Alive State
 */
public class AliveState implements ChapState {
	
	private Chap chap;
	
	public AliveState(Chap chap){
		this.chap = chap;
	}
	
	public void die(){
		chap.setState(chap.getDeadState());
	}
	
	public void revive() {
		//not ded
	}
	
	public void win(){
		chap.setState(chap.getWinState());
	}
}
