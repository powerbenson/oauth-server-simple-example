package tw.idb.leetcode.service;

import org.springframework.stereotype.Service;

@Service("alphabetBoardPath")
public class AlphabetBoardPath {

    public String alphabetBoardPath(String target) {
        StringBuilder answer = new StringBuilder();
        Position position = new Position(0, 0);

        for (int i = 0; i < target.length(); i++) {
            Path path = path(position, target.charAt(i));
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

    private class Position {
        int x;
        int y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

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

    private Position find(char character) {
        return new Position((character - 'a') % 5, (character - 'a') / 5);
    }

    private Path path(Position original, char character) {
        Position target = find(character);
        char[] vertical = verticalPath(target.y - original.y);
        char[] horizon = horizonPath(target.x - original.x);
        return new Path(target, vertical, horizon);
    }

    private char[] verticalPath(int verticalDiff) {
        return chars(Math.abs(verticalDiff), verticalDiff > 0 ? 'D' : 'U');
    }

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
