#include <bits/stdc++.h>

using namespace std;

#define sz(a) int((a).size())
#define forn(i, n) for (int i = 0; i < int(n); ++i)

// check for all "good" substring length of string s, consisting of only 2 chars (0-9) x and y
// total 100 permutations (n!)
int solve(const string& s, int x, int y) {
	int res = 0; // length of "good string" <- substring of s
	for (auto c : s)   // iterate thru char in s
        if (c - '0' == x) { // s내 char c와 x가 일치할때만,
		    ++res;            
		    swap(x, y);     // alternate char x and y
	}
	if (x != y && res % 2 == 1) // 길이가 홀수이고 x,y가 다르다면
		--res;
	return res;
}

void solve() {
	string s;
	cin >> s;
	int ans = 0;
	forn(x, 10) forn(y, 10) // nested for loop to generate combinations of length 2
		ans = max(ans, solve(s, x, y));
	cout << sz(s) - ans << endl; // min removal 
}

int main() {
	int T;
	cin >> T;
	while (T--) solve();
}