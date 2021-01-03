#include <iostream>
#include <cmath>

using namespace std;

int main() {

    int opResult;
    int invResult;
    int finResult;

    int income;
    int dep;
    char gainLoss;
    int change;

    cout <<"Enter net income: " << endl;
    cin >> income;
    opResult = income;

    cout <<"Enter depreciation: " << endl;
    cin >> dep;
    opResult += dep;

    cout <<"Was there a gain or loss on sale of equipment? (Enter 'g' for gain, 'l' for loss, or 'n' for none): " << endl;
    cin >> gainLoss;
    if (gainLoss == 'g') {
        cout <<"Enter change: " << endl;
        cin >> change;
        opResult -= change;
    } else if (gainLoss == 'l') {
        cout <<"Enter change: " << endl;
        cin >> change;
        opResult += change;
    } else if (gainLoss == 'n') {
        opResult += 0;
    } else {
        cout <<"Invalid input, please enter valid input: " << endl;
        cin >> gainLoss;
    }

    // curr assets
    int ar;
    int inv;
    int pp;

// increase in AR = -change = less it
    cout <<"Enter change in account receivable (previous year - current year): " << endl;
    cin >> ar;
    opResult += ar;

    cout <<"Enter change in inventory (previous year - current year): " << endl;
    cin >> inv;
    opResult += inv;

    cout <<"Enter change in prepaid insurance (previous year - current year): " << endl;
    cin >> pp;
    opResult += pp;

    char yn;
    string assetType;
    int assetChange;
    cout <<"Do you any other current assets? (y/n): " << endl;
    cin >> yn;
    if (yn == 'y') {
        cout << "Enter type of asset: " << endl;
        cin >> assetType;
        cout << "Enter change in asset: " << endl;
        cin >> assetChange;
        opResult += assetChange;
    } else {
        opResult += 0;
    }

        // curr liab;
    int ap;
    int ip;
    int ur;

// increase in AP = -change = add it
    cout <<"Enter change in account payable (previous year - current year): " << endl;
    cin >> ap;
    opResult -= ap;

    cout <<"Enter change in interest payable (previous year - current year): " << endl;
    cin >> ip;
    opResult -= ip;

    cout <<"Enter change in unearned revenue (previous year - current year): " << endl;
    cin >> ur;
    opResult -= ur;

    char yn2;
    string liabType;
    int liabChange;
    cout <<"Do you any other current liabilities? (y/n): " << endl;
    cin >> yn2;
    if (yn == 'y') {
        cout << "Enter type of liability: " << endl;
        cin >> liabType;
        cout << "Enter change in liability: " << endl;
        cin >> liabChange;
        opResult -= liabChange;
    } else {
        opResult += 0;
    }

    cout <<"Operations" << endl;
    cout <<"Net Income:     " << income << endl;
    if (gainLoss == 'g') {
        cout <<"Gain on sale:     " << "(" << gainLoss << ")" << endl;
    } else if (gainLoss == 'l') {
        cout <<"Loss on sale:     " << gainLoss << endl;
    }
    
    // cout <<"Account Receviables:     " << income << endl;
    cout <<"Cash flow from operations:     " << opResult << endl;
    

}

