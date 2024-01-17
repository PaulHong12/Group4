#include <iostream>
#include <vector>
using namespace std;

int main() {
    int n, k;
    cin >> n >> k;
    vector<int> a(n), t(n);
    for(int &ai : a) cin >> ai;
    for(int &ti : t) cin >> ti;

    int total = 0, extra = 0, maxExtra = 0;
    for(int i = 0; i < n; ++i) {
        if(t[i]) total += a[i];
        else if(i < k) extra += a[i]; //최초 window size 안 이라면

        if(i >= k) { // 최초 window size 넘어가면, 
            if(!t[i - k]) extra -= a[i - k];
            if(!t[i]) extra += a[i];
        }
        maxExtra = max(maxExtra, extra);
    }
    cout << total + maxExtra << endl;
    return 0;
}