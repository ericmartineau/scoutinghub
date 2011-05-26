package scoutinghub;

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.List;

/**
 * User: eric
 * Date: 4/12/11
 * Time: 9:14 PM
 */
public class ScoutGroupFilter {


//    private List<Integer> ids;
//
//    public ScoutGroupFilter(Integer... ids) {
//        this.ids = Arrays.asList(ids);
//    }
//
//    public BitSet bits(IndexReader reader) throws IOException {
//        BitSet bits = new BitSet(reader.maxDoc());
//
//        int[] docs = new int[1];
//        int[] freqs = new int[1];
//        for (Integer id : ids) {
//            if (id != null) {
//                TermDocs termDocs = reader.termDocs(new Term("scoutGroupId", String.valueOf(id)));
//                int count = termDocs.read(docs, freqs);
//                if (count == 1) {
//                    bits.set(docs[0]);
//                }
//            }
//        }
//        return bits;
//    }

    private static Query createQuery(Collection<String> visibleItems) {
        int maxTerms = BooleanQuery.getMaxClauseCount() - 1;
        int i = 0;
        boolean subQueryIsQuery = true;

        BooleanQuery query = new BooleanQuery();

        BooleanQuery subQuery = new BooleanQuery();
        for (String string : visibleItems) {
            subQuery.add(new TermQuery(new Term("scoutGroupId", string)), BooleanClause.Occur.SHOULD);
            if (i % maxTerms == 0) {
                subQueryIsQuery = false;
                query.add(new BooleanClause(subQuery, BooleanClause.Occur.SHOULD));
                subQuery = new BooleanQuery();
            }
            i++;
        }

        if (subQueryIsQuery) {
            return subQuery;
        }
        return query;
    }

    public static Filter createFilter(Collection<String> visibleItems) {
        Filter filter = new CachingWrapperFilter(new
                QueryWrapperFilter(createQuery(visibleItems)));
        return filter;
    }

}
