package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;



public class Posts_Chapter07 {

	public static void main(String[] args) {
		// TODO 自動生成されたメソッド・スタブ
        Connection con = null;
        PreparedStatement insertstatement = null;
        
        String[][] Postslist = {
        		{"1003","2023-02-08","昨日の夜は徹夜でした・・","13"},
        		{"1002","2023-02-08","お疲れ様です！","12"},
        		{"1003","2023-02-09","今日も頑張ります！","18"},
        		{"1001","2023-02-09","無理は禁物ですよ！","17"},
        		{"1002","2023-02-10","明日から連休ですね！","20"}		
        };
        
        try {
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost/challenge_java",
                "root",
                "Ao03030821."
            );

            System.out.println("データベース接続成功");
            System.out.println("レコード追加を実行します");
            
            String sql = "INSERT INTO posts(user_id, posted_at, post_content, likes) VALUES(?, ?, ?, ?);";
			insertstatement = con.prepareStatement(sql);
			
			int rowCnt = 0;
			for(String[] post : Postslist) {
				int user_id = Integer.parseInt(post[0]);
				String posted_at = post[1];
				String post_content = post[2];
				int likes = Integer.parseInt(post[3]);
				
				insertstatement.setInt(1, user_id);
				insertstatement.setString(2, posted_at);
				insertstatement.setString(3, post_content);
				insertstatement.setInt(4, likes);
				
	
				rowCnt = insertstatement.executeUpdate();
			
			} System.out.println("5件のレコードが追加されました");
			
			System.out.println("ユーザーIDが1002のレコードを検索しました");
			
			String serchsql = "SELECT posted_at, likes, post_content FROM posts WHERE user_id = 1002;";
			
			Statement serchstatement = con.createStatement();
			
			ResultSet result = serchstatement.executeQuery(serchsql);
			
			while(result.next()) {
				Date posted_at = result.getDate("posted_at");
				String post_content = result.getString("post_content");
				int likes = result.getInt("likes");
				System.out.println(result.getRow() + "件目:投稿日時=" + posted_at + "/投稿内容=" + post_content + "/いいね数=" + likes);			
			}
			serchstatement.close();
			
        }catch(SQLException e ) {
        	System.out.println("エラー発生:" + e.getMessage());
        }finally {
        	if(insertstatement != null) {
        		try {insertstatement.close();}catch(SQLException ignore) {}
        	}
        	if(con != null) {
        		try {con.close();}catch(SQLException ignore) {}
        	}
        }
	}
}