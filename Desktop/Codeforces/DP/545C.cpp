#include <iostream>
#include <vector>
using namespace std;

int main() {
    int n;
    cin >> n; // Number of trees

    vector<pair<int, int>> trees(n);
    for (int i = 0; i < n; ++i) {
        cin >> trees[i].first >> trees[i].second; // xi, hi
    }

    int count = 0; // Count of felled trees

    if (n == 1) {
        cout << 1 << endl;
        return 0;
    }

    // Fell the first tree to the left
    count = 1;

    for (int i = 1; i < n - 1; ++i) {
        // Fell to the left if there's space
        if (trees[i].first - trees[i].second > trees[i - 1].first) {
            count++;
        }
        // Otherwise, fell to the right if there's space
        else if (trees[i].first + trees[i].second < trees[i + 1].first) {
            count++;
            trees[i].first += trees[i].second; // Update the position to reflect the fallen tree, for the next tree!!
        }
    }

    // Always fell the last tree to the right
    count++;

    cout << count << endl; // Output the maximum number of felled trees
    return 0;
}