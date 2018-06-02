package Game2;

public class UserScore implements Comparable<UserScore>
{
    String name;
    int score;
    public UserScore(String name,String score) {
        this.name=name;
        this.score = Integer.parseInt(score);
    }
    @Override
    public int compareTo(UserScore o) {
            if (score!=o.score)
                return score>o.score? 1:-1;
            return name.compareTo(o.name);
    }
    
}
