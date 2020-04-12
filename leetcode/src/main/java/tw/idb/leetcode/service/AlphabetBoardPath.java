package tw.idb.leetcode.service;

import org.springframework.stereotype.Service;

/**
 * Alphabet Board Path
 *
 * 題目
 *
 * On an alphabet board, we start at position (0, 0), corresponding to character board[0][0].
 * Here, board = ["abcde", "fghij", "klmno", "pqrst", "uvwxy", "z"], as shown in the diagram below.
 *
 * a b c d e
 * f g h i j
 * k l m n o
 * p q r s t
 * u v w x y
 * z
 *
 * We may make the following moves:
 *
 * 'U' moves our position up one row, if the position exists on the board;
 * 'D' moves our position down one row, if the position exists on the board;
 * 'L' moves our position left one column, if the position exists on the board;
 * 'R' moves our position right one column, if the position exists on the board;
 * '!' adds the character board[r][c] at our current position (r, c) to the answer.
 *
 * (Here, the only positions that exist on the board are positions with letters on them.)
 * Return a sequence of moves that makes our answer equal to target in the minimum number of moves.  You may return any path that does so.
 *
 * Example 1:
 * Input: target = "leet"
 * Output: "DDR!UURRR!!DDD!"
 *
 * Example 2:
 * Input: target = "code"
 * Output: "RR!DDRR!UUL!R!"
 *
 * 本題需要注意的是: 'z'這個字元的特殊位置
 * 當其他字元向 'z' 進行移動時，必須先向左移動 ; 當'z'向其他字元移動時，必須先向上移動。
 * 否則就會出現路徑不合法的情況
 *
 */

@Service("alphabetBoardPath")
public class AlphabetBoardPath {

    public String alphabetBoardPath(String target) {
        StringBuilder answer = new StringBuilder();
        Position position = new Position(0, 0);

        for (int i = 0; i < target.length(); i++) {
            Path path = path(position, target.charAt(i));

            // 為了遞免非法路徑 此段程式碼要判斷優先權 如果目的地在下方，要先水平移動在垂直移動，相反，要先垂直移動在水平移動
            if (path.destination.y > position.y) {
                answer.append(path.horizon);
                answer.append(path.vertical);
            } else {
                answer.append(path.vertical);
                answer.append(path.horizon);
            }
            answer.append('!');
            position = path.destination;
        }
        return answer.toString();
    }

    // 位置資訊的結構
    private class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    // 路徑資訊的結構
    private class Path {
        Position destination;
        char[] vertical;
        char[] horizon;

        public Path(Position destination, char[] vertical, char[] horizon) {
            this.destination = destination;
            this.vertical = vertical;
            this.horizon = horizon;
        }
    }

    // 尋找位置
    private Position find(char character) {
        return new Position((character - 'a') % 5, (character - 'a') / 5);
    }

    // 回傳路徑
    private Path path(Position original, char character) {
        Position target = find(character);
        char[] vertical = verticalPath(target.y - original.y);
        char[] horizon = horizonPath(target.x - original.x);
        return new Path(target, vertical, horizon);
    }

    // 垂直路徑操作
    private char[] verticalPath(int verticalDiff) {
        return chars(Math.abs(verticalDiff), verticalDiff > 0 ? 'D' : 'U');
    }

    // 水平路徑操作
    private char[] horizonPath(int horizontalDiff) {
        return chars(Math.abs(horizontalDiff), horizontalDiff > 0 ? 'R' : 'L');
    }

    private char[] chars(int count, char character) {
        char[] x = new char[count];
        for (int i = 0; i < x.length; i++) {
            x[i] = character;
        }
        return x;
    }
}
