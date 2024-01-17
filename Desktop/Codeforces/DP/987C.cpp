#include <bits/stdc++.h>
using namespace std;

int main() {
    int n;
    cin >> n;

    vector<int> s(n), c(n);
    for (int &si : s) cin >> si;
    for (int &ci : c) cin >> ci;

    vector<int> dp(n, INT_MAX); // dp[i] will store the minimum cost for the second display in the sequence ending at i

    int minCost = INT_MAX;
    //hint, idea: THREE elements
    //DP: up to j!!, compare all pairs to get dp[i]
    for (int j = 1; j < n; ++j) {
        for(int i = 0; i < j; ++i) {
            if(s[j] > s[i]){
                dp[j] = min(dp[j], c[i]+c[j]);
            }
        }
        //DP: for k, from j+1 to the end, compare all triplets 
        for(int k = j+1; k < n; ++k) {
            if(s[k]>s[j])
                minCost = min(minCost, dp[j]+c[k]);
        }
    }

    if (minCost == INT_MAX) {
        cout << "-1\n"; // no valid sequence found
    } else {
        cout << minCost << "\n";
    }

    return 0;
}