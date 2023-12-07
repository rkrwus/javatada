package miniGame;

class User {
	private int rank;
	private String name;
	private int score;

//	신규 유저정보 입력할 때
	public User(int score) {
		this.score = score;
	}

//	랭킹 파일에서 유저 정보 불러와 객체 생성할 때
	public User(int rank, String name, int score) {
		this.rank = rank;
		this.name = name;
		this.score = score;
	}

	int getRank() {	return this.rank; }
	void setRank(int rank) { this.rank = rank; }

	String getName() { return this.name; }
	void setName(String name) { this.name = name; }

	int getScore() {return this.score; }
	void setScore(int score) { this.score = score; }

}