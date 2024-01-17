// prefix, suffix maxima
#include <bits/stdc++.h>
 
using namespace std;

int main() {
#ifdef _DEBUG
    freopen("input.txt", "r", stdin);
//  freopen("output.txt", "w", stdout);
#endif
    
    int n;
    cin >> n;
    vector<int> a(n);
    for (auto &it : a) cin >> it;
    
    vector<int> pref(n), suf(n);

    //prefix/suffix maxima 템플릿: default value initialization
    //solution 로직, 코드를 정확히 이해하자. line by line!!
    for (int i = 0; i < n; ++i) {
        pref[i] = a[i] + (n - i - 1); // i 오른쪽 먼저 하고 i를 마지막에 하기 위한 최솟값
        suf[i] = a[i] + i; // i 왼쪽 먼저 하고 i를 마지막에 하기 위한 최솟값
    }
    
    //prefix/suffix maxima 템플릿: calculate pre&suff maxima/minima
    for (int i = 1; i < n; ++i) { //시작부터 i까지 필요한 최소 SP
    //prefix: up to i
        pref[i] = max(pref[i], pref[i - 1]);
    }
    for (int i = n - 2; i >= 0; --i) { //마지막부터 i까지 필요한 최소 SP
    //suffix: starting from i
        suf[i] = max(suf[i], suf[i + 1]);
    }
    
    //답 구하기. using pre/suffix maxima, i, pre[i], suf[i]를 모두 O(n)만에 해결 가능하다!!
    int ans = 2e9;
    //모든 i(시작점) 마다 i 이전, i이후 최소 필요 SP와 비교한다.
    for (int i = 0; i < n; ++i) {
        int cur = a[i]; // (중요)solution logic: i에서 시작한다! 그래서 i값 그대로가 필요하다.
        //모든 i에서,  pref[]와 suf[], a[i] 중 전체 최댓값이 정답이다.
        if (i > 0) cur = max(cur, pref[i - 1]);
        if (i + 1 < n) cur = max(cur, suf[i + 1]);
        ans = min(ans, cur);
    }
    
    cout << ans << endl;
    
    return 0;
}