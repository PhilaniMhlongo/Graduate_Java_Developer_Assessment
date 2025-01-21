
DROP TABLE IF EXISTS recycling_tip;
DROP TABLE IF EXISTS disposal_guideline; 
DROP TABLE IF EXISTS waste_category;

CREATE TABLE waste_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT NOT NULL
);

CREATE TABLE disposal_guideline (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    waste_category_id INT NOT NULL,
    guideline TEXT NOT NULL,
    FOREIGN KEY (waste_category_id) REFERENCES waste_category(id) ON DELETE CASCADE
);

CREATE TABLE recycling_tip (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    waste_category_id INT NOT NULL,
    tip TEXT NOT NULL,
    FOREIGN KEY (waste_category_id) REFERENCES waste_category(id) ON DELETE CASCADE
);


INSERT INTO waste_category (id, name, description) VALUES
(1, 'Plastic', 'Materials made of synthetic or semi-synthetic organic compounds that can be molded into solid objects'),
(2, 'Paper', 'Products made from wood pulp or recycled paper materials including cardboard and newspapers'),
(3, 'Glass', 'Products made from silica including bottles, jars, and broken glass items'),
(4, 'Metal', 'Ferrous and non-ferrous metals including aluminum cans, steel containers, and scrap metal'),
(5, 'Organic', 'Biodegradable waste from food and garden materials'),
(6, 'Electronic', 'Discarded electrical or electronic devices and their parts'),
(7, 'Hazardous', 'Materials that are potentially dangerous to human health or the environment'),
(8, 'Textile', 'Clothing, fabrics, and other fiber-based materials'),
(9, 'Construction', 'Debris and materials from building, renovation, and demolition activities'),
(10, 'Medical', 'Healthcare-related waste requiring special handling');

-- Insert data into disposal_guideline table
INSERT INTO disposal_guideline (waste_category_id, guideline) VALUES
(1, 'Clean and dry plastic items before disposing of them.'),
(1, 'Avoid disposing of non-recyclable plastics.'),
(2, 'Break down the box completely flat'),
(2, 'Remove any tape, staples, or plastic materials'),
(2, 'Keep dry and clean'),
(2, 'Bundle with other cardboard or place in recycling bin'),
(4, 'Rinse metal cans before placing them in the bin.'),
(4, 'Separate metal lids from glass jars before disposal.'),
(3, 'Do not dispose of broken glass in regular recycling bins.'),
(5, 'Separate metal lids from glass jars before disposal.'),
(6, 'Back up all important data'),
(6, 'Perform a secure data wipe'),
(6, 'Remove any batteries'),
(6, 'Take to certified e-waste recycler');

-- Insert data into recycling_tip table
INSERT INTO recycling_tip (waste_category_id, tip) VALUES
(1, 'Learn to identify different plastic types by their recycling numbers. #1 (PET) and #2 (HDPE) are the most commonly recycled plastics.'),
(1, 'Use reusable water bottles and shopping bags to minimize plastic waste. Small changes can make a big difference!'),
(2, 'Print double-sided and use digital alternatives when possible. Keep paper products dry and clean for recycling.'),
(4, 'Recycling aluminum uses 95% less energy than producing new aluminum. Always crush cans to save space.'),
(5, 'Start with a mix of green materials (food scraps) and brown materials (dried leaves). Keep your compost pile moist but not wet.'),
(6, 'Always securely wipe data from electronic devices before recycling. Many manufacturers offer take-back programs.'),
(7, 'Let latex paint dry out completely before disposal. Oil-based paints require special handling.'),
(1, 'Use a washing bag for synthetic clothes and avoid microbeads in personal care products to reduce microplastic pollution.'),
(5, 'Plan meals, use shopping lists, and store food properly to reduce organic waste. Compost unavoidable food scraps.'),
(3, 'Glass can be recycled indefinitely without loss in quality. Always rinse containers and separate by color when required.');