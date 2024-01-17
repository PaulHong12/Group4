#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

long long maxPoints(const vector<int>& a) {  // const: final, not modifying the content.
    int max_val = *max_element(a.begin(),a.end()); // bc it returns an iterator
    vector<long long> count(max_val+1, 0); // vector<type> name(size, default value)

    // count: Frequency array/map in O(n)
    for(int num: a)
        count[num]++;

    vector<long long> dp(max_val+1, 0);
    dp[1] = count[1];  // 첫번째 까지의 최대는 첫번째를 선택한 것. 

    // Fill the DP array using the relation we've discussed.
    for (int i = 2; i <= max_val; ++i) {
        dp[i] = max(dp[i - 1], dp[i - 2] + i * count[i]);  //max(현재 포함 안한것, 한것)
    }

    return dp[max_val];
}

int main() {
    int n;
    cin >> n;

    vector<int> a(n);

    for(int i = 0; i< n; i++){
        cin >> a[i];
    }

    cout << maxPoints(a) << endl; 
    return 0;
}