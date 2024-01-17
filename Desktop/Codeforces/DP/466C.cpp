#include <iostream>
#include <vector>

int main() {
    int n;
    std::cin >> n;
    std::vector<long long> a(n);
    long long sum = 0;

    for (int i = 0; i < n; ++i) {
        std::cin >> a[i];
        sum += a[i];
    }

    if (sum % 3 != 0) {
        std::cout << 0 << std::endl; // If total sum is not divisible by 3, no way to split into 3 equal parts
        return 0;
    }

    long long sum1 = 0;
    long long cnt1 = 0;
    long long cnt2 = 0;

    // Iterate through the array, except for the last element bc dividing into 3 parts.
    for (int i = 0; i < n - 1; ++i) {
        sum1 += a[i];

        if (sum1 == 2*sum/3) {
            cnt2 += cnt1;
        }

        if (sum1 == sum/3) {
            cnt1++;
        }
    }

    std::cout << cnt2 << std::endl;
    return 0;
}
