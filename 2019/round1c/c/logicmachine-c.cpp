#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
#include <set>
#include <cstring>

using namespace std;
static int grundy[16][16][16][16];

int solve(
	int r0, int c0, int r1, int c1,
	const vector<string>& field)
{
	if(r0 == r1 || c0 == c1){ return 0; }
	if(grundy[r0][c0][r1][c1] >= 0){ return grundy[r0][c0][r1][c1]; }
	set<int> st;
	// Vertical
	for(int c = c0; c < c1; ++c){
		bool accept = true;
		for(int r = r0; r < r1; ++r){
			if(field[r][c] == '#'){ accept = false; }
		}
		if(!accept){ continue; }
		st.insert(solve(r0, c0, r1, c, field) ^ solve(r0, c + 1, r1, c1, field));
	}
	// Horizontal
	for(int r = r0; r < r1; ++r){
		bool accept = true;
		for(int c = c0; c < c1; ++c){
			if(field[r][c] == '#'){ accept = false; }
		}
		if(!accept){ continue; }
		st.insert(solve(r0, c0, r, c1, field) ^ solve(r + 1, c0, r1, c1, field));
	}
	// Grundy
	int g = 0;
	for(const auto& x : st){
		if(g < x){ break; }
		++g;
	}
	grundy[r0][c0][r1][c1] = g;
	return g;
}

int main(){
	ios_base::sync_with_stdio(false);
	int num_cases;
	cin >> num_cases;
	for(int case_num = 1; case_num <= num_cases; ++case_num){
		int h, w;
		cin >> h >> w;
		vector<string> field(h);
		for(int i = 0; i < h; ++i){ cin >> field[i]; }
		memset(grundy, -1, sizeof(grundy));
		const int g = solve(0, 0, h, w, field);
		int answer = 0;
		if(g > 0){
			// Vertical
			for(int c = 0; c < w; ++c){
				bool accept = true;
				for(int r = 0; r < h; ++r){
					if(field[r][c] == '#'){ accept = false; }
				}
				if(!accept){ continue; }
				if((solve(0, 0, h, c, field) ^ solve(0, c + 1, h, w, field)) == 0){
					answer += h;
				}
			}
			// Horizontal
			for(int r = 0; r < h; ++r){
				bool accept = true;
				for(int c = 0; c < w; ++c){
					if(field[r][c] == '#'){ accept = false; }
				}
				if(!accept){ continue; }
				if((solve(0, 0, r, w, field) ^ solve(r + 1, 0, h, w, field)) == 0){
					answer += w;
				}
			}
		}
		cout << "Case #" << case_num << ": " << answer << endl;
	}
	return 0;
}

