#include <iostream>
#include <vector>
using namespace std;

const int MOD = 1000000007;

int main() {
    int n, k;
    cin >> n >> k;
    //dp[len][last val]
    vector<vector<int>> dp(k + 1, vector<int>(n + 1, 0));

    // Base case: any sequence of length 1 is a good sequence
    for(int j = 1; j <= n; ++j)
        dp[1][j] = 1;

    // Filling the dp table, 
    for(int i = 1; i < k; ++i){
        for(int j = 1; j <= n ; ++j){
            for(int d = 1; d*j <= n; ++d){
                // push dp!! intuitive!!
                dp[i+1][j*d] = (dp[i+1][j*d] + dp[i][j])%MOD;
            }
        }   
    }

    // Calculating the answer
    int answer = 0;
    for (int j = 1; j <= n; ++j) {
        answer = (answer + dp[k][j]) % MOD;
    }

    cout << answer << endl;

    return 0;
}

