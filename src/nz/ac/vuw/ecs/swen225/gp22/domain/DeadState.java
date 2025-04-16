package nz.ac.vuw.ecs.swen225.gp22.domain;

public class DeadState implements ChapState{
	
	private Chap chap;
	
	public DeadState(Chap chap){
		this.chap = chap;
	}
	
	public void die(){
		//already ded
	}
	
	public void revive() {
		chap.setState(chap.getAliveState());
	}
	
	public void win(){
		//ur ded
	}
}
