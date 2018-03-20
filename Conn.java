import com.mongodb.*;

import static java.lang.System.out;
import java.net.UnknownHostException;

@SuppressWarnings("deprecation")
public class Conn {
  MongoClient mongoClient = null;
  DB database = null;
  DBCollection collection = null;

  public Conn(MongoClient mc) {
    this.mongoClient = mc;
  }

  public final void useDataBase(String dbName) {
    this.database = this.mongoClient.getDB(dbName);
    out.println(this.database);
  }
  
  public final void useCollection(String collectionName) {
    this.collection = this.database.getCollection(collectionName);
    out.println(this.collection);
  }

  public final void readCollection() {
    // fetch all documents through DBCursor
    /* try (DBCursor cursor = this.collection.find()) {
      while (cursor.hasNext()){
        out.println(cursor.next());
      }
      out.println();
    } */

    // fetch all documents through DBQuery
    BasicDBObject query = new BasicDBObject("difficulty", "easy");
    try (DBCursor cursor = this.collection.find(query)) {
      while(cursor.hasNext()) {
        out.println(cursor.next());
      }
    }
  }

  @Override
  public void finalize() throws Throwable {
    this.mongoClient.close();
  }

  public static void main(String...args) 
  throws UnknownHostException {
    Conn conn = new Conn(new MongoClient(new MongoClientURI("mongodb://localhost:27017")));
    out.println(conn);
    conn.useDataBase("quiz-app");
    out.println();
    conn.useCollection("questions");
    out.println();
    conn.readCollection();
  }
}