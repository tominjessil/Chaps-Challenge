package nz.ac.vuw.ecs.swen225.gp22.domain;

public class WinState implements ChapState{

	private Chap chap;
	
	public WinState(Chap chap){
		this.chap = chap;
	}
	
	public void die() {
		//too late
	}
	
	public void revive() {
		chap.setState(chap.getAliveState());
	}

	public void win() {}
	
}
