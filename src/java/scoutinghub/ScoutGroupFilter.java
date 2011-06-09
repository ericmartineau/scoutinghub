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

    private static Query createQuery(Collection<String> visibleItems) {
        int maxTerms = BooleanQuery.getMaxClauseCount() - 1;
        int i = 0;
        boolean subQueryIsQuery = true;

        BooleanQuery query = new BooleanQuery();

        BooleanQuery subQuery = new BooleanQuery();
        subQuery.setMinimumNumberShouldMatch(1);
        for (String string : visibleItems) {
            StringBuilder prefix = new StringBuilder();
            for (int x = 0; x < 6; x++) {
                if (x > 0) {
                    prefix.append("parent_");
                }

                subQuery.add(new TermQuery(new Term(prefix.toString() + "scoutGroupId", string)), BooleanClause.Occur.SHOULD);
                if (i % maxTerms == 0) {
                    subQueryIsQuery = false;
                    query.add(new BooleanClause(subQuery, BooleanClause.Occur.SHOULD));
                    subQuery = new BooleanQuery();
                }

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
