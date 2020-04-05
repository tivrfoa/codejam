#include <bits/stdc++.h>
using namespace std;
template<typename T = int> vector<T> create(size_t n){ return vector<T>(n); }
template<typename T, typename... Args> auto create(size_t n, Args... args){ return vector<decltype(create<T>(args...))>(n, create<T>(args...)); }
int main(){
	ios::sync_with_stdio(false);
	cin.tie(0);
	int t, b;
	cin >> t >> b;
	for(int _ = 0; _ < t; _++){
		string ans(b, 'x');
		vector<int> ord;
		for(int i = 1; i < b - i + 1; i++){
			ord.push_back(i);
			ord.push_back(b - i + 1);
		}
		if(b & 1) ord.push_back((b + 1) / 2);
		int ch = 1, at = 0;
		while(true){
			if(count(ans.begin(), ans.end(), 'x') == 0){
				cout << ans << endl;
				char c;
				cin >> c;
				if(c == 'N') return 0;
				break;
			}
			if(ch > 10 && (ch%10) == 1){
				for(int i = 1; i < b - i + 1; i++){
					if(ans[i - 1] == 'x' && ans[b - i] != 'x'){
						at--;
						ans[b - i] = 'x';
						continue;
					}
					if(ans[i - 1] != 'x' && ans[b - i] == 'x'){
						at--;
						ans[i - 1] = 'x';
						continue;
					}
				}
				for(int i = 0; i < b; i++){
					if(ans[i] == 'x'){
						int j = at;
						for(; j < ord.size(); j++) if(ord[j] == i + 1) break;
						assert(j < ord.size());
					}
				}
				int l = -1, r = -1;
				int p = -1, q = -1;
				for(int i = 0; i < b - i - 1; i++){
					int j = b - i - 1;
					if(ans[i] != 'x' && ans[j] != 'x'){
						if(ans[i] == ans[j]){
							l = i;
							r = j;
						}
						if(ans[i] != ans[j]){
							p = i;
							q = j;
						}
					}
				}
				bool swp = false, flip = false;
				if(l != -1){
					ch++;
					cout << l + 1 << endl;
					char op;
					cin >> op;
					if(op != ans[l]) flip = true;
				}
				if(flip){
					for(int i = 0; i < b; i++) if(ans[i] != 'x'){
						ans[i] = ans[i] == '0' ? '1' : '0';
					}
				}
				if(p != -1){
					ch++;
					cout << p + 1 << endl;
					char op;
					cin >> op;
					if(op != ans[p]) swp = true;
				}
				if(swp){
					for(int i = 1; i < b - i + 1; i++){
						swap(ans[i - 1], ans[b - i]);
					}
				}
				continue;
			}
			ch++;
			cout << ord[at] << endl;
			cin >> ans[ord[at] - 1];
			at++;
		}
	}
	return 0;
}
