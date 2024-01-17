

string checkTwoSubstrings(string s) {
    int firstAB = -1, firstBA = -1;

    for (int i = 0; i < s.length() - 1; ++i) {
        if (s[i] == 'A' && s[i + 1] == 'B') {
            if (firstAB == -1) {
                firstAB = i;
            } 
            if (firstBA != -1 && i - firstBA > 1) {
                return "YES";
            }
        } else if (s[i] == 'B' && s[i + 1] == 'A') {
            if (firstBA == -1) {
                firstBA = i;
            } 
            if (firstAB != -1 && i - firstAB > 1) {
                return "YES";
            }
        }
    }
    return "NO";
}

int main() {
    string s;
    cin >> s;
    cout << checkTwoSubstrings(s) << endl;
    return 0;
}
